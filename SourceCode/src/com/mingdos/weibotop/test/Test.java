/**
 * 
 */
package com.mingdos.weibotop.test;
import com.mingdos.weibotop.dao.*;
import com.mingdos.weibotop.logger.*;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.mingdos.weibotop.model.*;
import com.mingdos.weibotop.weibotopcrawler.*;
/**
 * @author mingda.cai
 *
 */
public class Test {
	
	private static Integer delaySeconds = 900; // Crawl the page every 15 mins mingda: to modify 900
	private static Integer maxNumberOfTopics = 200; //mingda: to modify 200

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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		CrawlerLogger crawlerLog = CrawlerLogger.getInstance();
		
		while(true) {
			
			try {
				
				now = LocalDateTime.now();
				TimeUnit.SECONDS.sleep(delaySeconds);
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
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				crawlerLog.log(e.toString());
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				crawlerLog.log(e.toString());
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				crawlerLog.log(e.toString());
				e.printStackTrace();
			}
		}

	}

}
