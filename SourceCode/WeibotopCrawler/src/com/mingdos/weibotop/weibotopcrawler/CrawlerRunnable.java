package com.mingdos.weibotop.weibotopcrawler;

public class CrawlerRunnable implements Runnable {

	public CrawlerRunnable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Crawler.startCrawler();
	}

}
