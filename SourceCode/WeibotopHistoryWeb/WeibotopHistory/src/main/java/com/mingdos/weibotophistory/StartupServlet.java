package com.mingdos.weibotophistory;

import com.mingdos.weibotop.weibotopcrawler.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class StartupServlet
 */
public class StartupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void init() throws ServletException {
		System.out.println("---------------------");
		System.out.println("--- Tomcat Startup --");
		System.out.println("---------------------");
		
		CrawlerRunnable cr = new CrawlerRunnable();
		Thread t = new Thread(cr);
		t.start();
	}

}
