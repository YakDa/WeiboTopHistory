/**
 * 
 */
package com.mingdos.weibotop.crawler;
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
public class WeiboTopCrawler {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Crawler.startCrawler();

	}

}
