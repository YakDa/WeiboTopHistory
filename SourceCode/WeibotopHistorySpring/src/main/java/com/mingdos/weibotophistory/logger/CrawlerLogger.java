package com.mingdos.weibotophistory.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
//@Aspect
//@EnableAspectJAutoProxy
@ConfigurationProperties(prefix = "crawler")
public class CrawlerLogger {

	private boolean debugMode;
	private String path;
	private DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter dftFile = DateTimeFormatter.ofPattern("yyyyMMdd"); //mingda: to modify yyyyMMdd
	
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public CrawlerLogger() {
		LocalDateTime now = LocalDateTime.now();
		if(debugMode == true) {
			path = "C:\\Github\\MyRepositary\\WeiboTopHistory\\SourceCode\\WeibotopHistoryWeb\\" + "Crawler_" + dftFile.format(now) + ".log";
		}
		else {
			path = "/home/caimingda1987/tomcat/apache-tomcat-8.5.56/logs/" + "Crawler_" + dftFile.format(now) + ".log";
		}
	}
	
	public void log(String info) {
		
		LocalDateTime now = LocalDateTime.now();

		File fout = new File(path);
		
		try {
			FileWriter fw = new FileWriter(fout, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dtfLog.format(now) + ": " + info);
			bw.newLine();

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
