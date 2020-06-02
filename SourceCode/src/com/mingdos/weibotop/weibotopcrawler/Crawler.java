/**
 * 
 */
package com.mingdos.weibotop.weibotopcrawler;
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
	public static final String version = "Crawler 1.0";
	
	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * 
	 */
	public static List<Topic> crawl(String url) throws IOException, InterruptedException, ParseException {
		
		
		List<Topic> currentTopList = new ArrayList<>();
		Document topPage = Jsoup.connect(url).get();
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
					Document weiboPage = Jsoup.connect(subUrl).get();
					content = weiboPage.getElementsByTag("p").text();
					content = content.replace("上传失败，你的图片过小，需要上传1000x300像素以上的图片", "");
					if(content.length()>300)
						content = content.substring(0, 300);
					// delay 10 seconds to skip Weibo anti-robot checking
					TimeUnit.SECONDS.sleep(10);
				}
				id = Long.toString(Long.parseLong(id) + 1);
				if(weiboMap.containsKey(topic)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Topic ref = weiboMap.get(topic);
					Long lastDate = sdf.parse(prevCrawlTime).getTime()/1000;
					Long currentDate = sdf.parse(lastTime).getTime()/1000;
					ref.setLastTime(lastTime);
					ref.setDuration(ref.getDuration() + currentDate - lastDate);
					if(ref.getHighestRank() > highestRank) {
						ref.setHighestRank(highestRank);
					}
					if(ref.getHotpoints() < hotpoints) {
						ref.setHotpoints(hotpoints);
					}
					currentTopList.add(ref);
				}
				else {
					Topic newTopic = new Topic(id, topic, content, "", highestRank, hotpoints, firstTime, firstTime, (long)0);
					weiboMap.put(topic, newTopic);
					currentTopList.add(newTopic);
				}
			}
		}
		
		prevCrawlTime = lastTime;
		CrawlerLogger crawlerLog = CrawlerLogger.getInstance();
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

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");

		for(int i=0;i <= 10; i++) {
			LocalDateTime now = LocalDateTime.now();
			System.out.println("Start: " + dtf.format(now));
			List<Topic> result = null;
			try {
				result = crawl("https://s.weibo.com/top/summary?cate=realtimehot");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Topic m: result) {
				System.out.println(m.getHighestRank() + " " + m.getId() + " " + m.getTopic() + " " + m.getHotpoints());
				System.out.println(m.getContent());
			}
			now = LocalDateTime.now();
			System.out.println("End: " + dtf.format(now));
		}

		return;
	}
}
