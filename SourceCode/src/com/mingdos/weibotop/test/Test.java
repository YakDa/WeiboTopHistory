/**
 * 
 */
package com.mingdos.weibotop.test;
import com.mingdos.weibotop.dao.*;
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
	
	private static Integer delaySeconds = 900; // Crawl the page every 15 mins 
	private static Integer maxNumberOfTopics = 100;

	public static void loadDataToDB(List<Topic> topics) {
		WeiboTopDao dao = new WeiboTopDaoImpl();
		
		for(Topic t: topics) {
			if(dao.isTopicExist(t.getTopic()) == false) {
				if(!dao.insertTopic(t)) {
					System.out.println("Failed to insert to database");
				}
			}
			else {
				if(!dao.updateTopic(t)) {
					System.out.println("Failed to update to database");
				}
			}
		}
		
		Crawler.clearAllTopics();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true) {
			
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
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
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
