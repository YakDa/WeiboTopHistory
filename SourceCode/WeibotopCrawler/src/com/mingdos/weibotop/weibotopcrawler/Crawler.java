/**
 * 
 */
package com.mingdos.weibotop.weibotopcrawler;
import com.mingdos.weibotop.dao.WeiboTopDao;
import com.mingdos.weibotop.dao.WeiboTopDaoImpl;
import com.mingdos.weibotop.logger.CrawlerLogger;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import com.mingdos.weibotop.model.*;
/**
 * @author mingda.cai
 *
 */
public class Crawler {

	private static HashMap<String, Topic> weiboMap = new HashMap<>();
	private static String prevCrawlTime;
	public static final String version = "Crawler 1.1";
	private static Integer delaySeconds = 9; // Crawl the page every 15 mins mingda: to modify 900
	private static Integer maxNumberOfTopics = 51; //mingda: to modify 200
	
	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * 
	 */
	public static List<Topic> crawl(String url) {
		
		CrawlerLogger crawlerLog = CrawlerLogger.getInstance();
		List<Topic> currentTopList = new ArrayList<>();
		Document topPage;
		try {
			topPage = Jsoup.connect(url).get();
		} catch (IOException e1) {
			crawlerLog.log(e1.toString());
			return currentTopList;
		}
		Elements tbodys = topPage.getElementsByTag("tbody");
		Elements trs = tbodys.get(0).getElementsByTag("tr");
		
		//YYYYMMDDHHMMSSII
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String id = dtf.format(now);
		dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String firstTime = dtf.format(now);
		String lastTime = dtf.format(now);
		
		for(Element e: trs) {
			if(e.getElementsByTag("td").get(0).hasClass("td-01 ranktop")) {
				Long highestRank = Long.parseLong(e.getElementsByClass("td-01 ranktop").get(0).text());
				Element td2 = e.getElementsByClass("td-02").get(0);
				Element link = td2.getElementsByTag("a").get(0);
				String topic = link.text();
				Long hotpoints = Long.parseLong(td2.getElementsByTag("span").get(0).text());
				String content = "";
				if(!link.attr("href").equals("javascript:void(0);")) {
					String subUrl = "https://s.weibo.com" + link.attr("href");
					Document weiboPage;
					try {
						weiboPage = Jsoup.connect(subUrl).get();
						content = weiboPage.getElementsByTag("p").text();
						content = content.replace("上传失败，你的图片过小，需要上传1000x300像素以上的图片", "");
						if(content.length()>300)
							content = content.substring(0, 300);
						// delay 10 seconds to skip Weibo anti-robot checking
						TimeUnit.SECONDS.sleep(10);
					} catch (IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						// if there are exception, then just continue to get the next topic, just consider this topic is empty.
						e1.printStackTrace();
					}
					
				}
				id = Long.toString(Long.parseLong(id) + 1);
				if(weiboMap.containsKey(topic)) {

					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Topic ref = weiboMap.get(topic);
						Long lastDate;
						lastDate = sdf.parse(prevCrawlTime).getTime()/1000;
						Long currentDate = sdf.parse(lastTime).getTime()/1000;
						ref.setLastTime(lastTime);
						ref.setDuration(ref.getDuration() + currentDate - lastDate);
						if(ref.getHighestRank() > highestRank) {
							ref.setHighestRank(highestRank);
						}
						if(ref.getHotpoints() < hotpoints) {
							ref.setHotpoints(hotpoints);
						}
						if(!content.isEmpty()) {
							if(ref.getContent().isEmpty() || ref.getContent().startsWith("Copyright © ")) {
								ref.setContent(content);
							}
						}
						currentTopList.add(ref);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						// if There is exception for the parsing time, then just continue, consider there is period of time the 
						// topic is missed on the top list.
						e1.printStackTrace();
					}

				}
				else {
					Topic newTopic = new Topic(id, topic, content, "", highestRank, hotpoints, firstTime, firstTime, (long)0);
					weiboMap.put(topic, newTopic);
					currentTopList.add(newTopic);
				}
			}
		}
		
		prevCrawlTime = lastTime;
		crawlerLog.log(currentTopList.size() + " topics crawled");
		
		return currentTopList;
		
	}
	
	public static List<Topic> getAllTopics() {
		return new ArrayList<Topic>(weiboMap.values());
	}
	
	public static int getNumberOfTopics() {
		return weiboMap.size();
	}
	
	public static void clearAllTopics() {
		weiboMap.clear();
	}
	
	public static void loadDataToDB(List<Topic> topics) {

		int numOfUpdated = 0;
		int numOfInserted = 0;
		WeiboTopDao dao = new WeiboTopDaoImpl();
		CrawlerLogger crawlerLog = CrawlerLogger.getInstance();
		
		for(Topic t: topics) {
			if(dao.isTopicExist(t.getTopic()) == false) {
				if(!dao.insertTopic(t)) {
					System.out.println("Failed to insert to database");
					crawlerLog.log("[DB]" + " Failed to insert to database");
				}
				numOfInserted++;
			}
			else {
				if(!dao.updateTopic(t)) {
					System.out.println("Failed to update to database");
					crawlerLog.log("[DB]" + " Failed to update to database");
				}
				numOfUpdated++;
			}
		}
		crawlerLog.log("[DB]" + numOfInserted + " topics inserted");
		crawlerLog.log("[DB]" + numOfUpdated + " topics updated");
		Crawler.clearAllTopics();
	}
	
	public static void startCrawler() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		CrawlerLogger crawlerLog = CrawlerLogger.getInstance();
		
		while(true) {
			
			try {
				
				now = LocalDateTime.now();
				
				System.out.println("Start: " + dtf.format(now));
				List<Topic> result = Crawler.crawl("https://s.weibo.com/top/summary?cate=realtimehot");
				

				for(Topic t: result) {
					
					System.out.println(t.getHighestRank() + " " + t.getId() + " " + t.getTopic() + " " + t.getHotpoints());
					System.out.println(t.getContent());
					System.out.println(t.getFirstTime() + " --- " + t.getLastTime() + " --- " + t.getDuration() + " Seconds");
					
				}
				
				if(Crawler.getNumberOfTopics() > maxNumberOfTopics) {
					loadDataToDB(Crawler.getAllTopics());
				}
				
				
				now = LocalDateTime.now();
				System.out.println("End: " + dtf.format(now));
				
				TimeUnit.SECONDS.sleep(delaySeconds);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				crawlerLog.log(e.toString());
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub


		return;
	}
}
