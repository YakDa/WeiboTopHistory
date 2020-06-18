package com.mingdos.weibotop.weibotopcrawler;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("---------------------");
		System.out.println("--- Tomcat Startup --");
		System.out.println("---------------------");
		
		CrawlerRunnable cr = new CrawlerRunnable();
		Thread t = new Thread(cr);
		t.start();
	}

}
