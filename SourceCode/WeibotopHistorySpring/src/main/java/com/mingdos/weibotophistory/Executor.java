package com.mingdos.weibotophistory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.mingdos.weibotophistory.weibotopcrawler.CrawlerRunnable;

@Component
public class Executor {
	
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private ApplicationContext context;
	@PostConstruct
	public void atStartup( ) {
		CrawlerRunnable crawlerRunnable = context.getBean(CrawlerRunnable.class);
		taskExecutor.execute(crawlerRunnable);
	}
	
}
