package com.mingdos.weibotophistory.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@ConfigurationProperties(prefix = "crawler")
public class CrawlerLogger {

	private boolean debugMode;
	
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	private DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter dftFile = DateTimeFormatter.ofPattern("yyyyMMdd"); //mingda: to modify yyyyMMdd
	
	

	
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
			bw.write(dtfLog.format(now) + ": " + info);
			bw.newLine();

			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public static CrawlerLogger getInstance(boolean debug) {
//		
//		debugMode = debug;
//		
//		if(crawlerLogger == null) {
//			crawlerLogger = new CrawlerLogger();
//		}
//		return crawlerLogger;
//	}

}
