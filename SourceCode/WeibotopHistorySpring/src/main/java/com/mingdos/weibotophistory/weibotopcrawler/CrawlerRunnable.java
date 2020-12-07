package com.mingdos.weibotophistory.weibotopcrawler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CrawlerRunnable implements Runnable {

	@Autowired
	Crawler crawler;
	public CrawlerRunnable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		crawler.startCrawler();
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ApplicationContext context = SpringApplication.run(CrawlerRunnable.class, args);
		
		CrawlerRunnable cr = context.getBean(CrawlerRunnable.class);
		Thread t = new Thread(cr);
		t.start();
		
		return;
	}

}
