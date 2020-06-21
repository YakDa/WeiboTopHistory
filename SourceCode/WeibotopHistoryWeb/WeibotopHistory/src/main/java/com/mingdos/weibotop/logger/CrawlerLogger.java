package com.mingdos.weibotop.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.mingdos.weibotop.weibotopcrawler.*;

public class CrawlerLogger {

	private static CrawlerLogger crawlerLogger;
	private static boolean debugMode;
	private DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter dftFile = DateTimeFormatter.ofPattern("yyyyMMdd"); //mingda: to modify yyyyMMdd
	
	private CrawlerLogger() {
		// Set the constructor of Singleton class to private to avoid initialization from outside
	}
	

	
	public void log(String info) {
		
		LocalDateTime now = LocalDateTime.now();
		String path;
		if(debugMode == true) {
			path = "C:\\Github\\MyRepositary\\WeiboTopHistory\\SourceCode\\WeibotopHistoryWeb\\" + "Crawler_" + dftFile.format(now) + ".log";
		}
		else {
			path = "/home/caimingda1987/tomcat/apache-tomcat-8.5.56/logs/" + "Crawler_" + dftFile.format(now) + ".log";
		}
		
		File fout = new File(path);
		
		try {
			FileWriter fw = new FileWriter(fout, true);
			BufferedWriter bw = new BufferedWriter(fw);
//			if(fout.exists() && !fout.isDirectory()) {
//				bw.write(info);
//				bw.newLine();
//			}
//			else {
//				bw.write(info);
//				bw.newLine();
//			}
			bw.write(dtfLog.format(now) + " (" + Crawler.version + "): " + info);
			bw.newLine();

			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static CrawlerLogger getInstance(boolean debug) {
		
		debugMode = debug;
		
		if(crawlerLogger == null) {
			crawlerLogger = new CrawlerLogger();
		}
		return crawlerLogger;
	}

}
