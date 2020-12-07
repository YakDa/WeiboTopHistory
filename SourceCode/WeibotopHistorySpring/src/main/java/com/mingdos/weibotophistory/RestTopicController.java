package com.mingdos.weibotophistory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mingdos.weibotophistory.exceptions.TestException;
import com.mingdos.weibotophistory.model.Topic;
import com.mingdos.weibotophistory.repository.TopicRepo;

@RestController
public class RestTopicController {
	
	@Autowired
	TopicRepo topicRepo;

	@GetMapping("today")
	public List<Topic> getTodayTopics() {
		
		DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String now = dtfLog.format(LocalDateTime.now());
		List<Topic> result = topicRepo.getTopicsFromToDate(now + " 00:00:00", now + " 23:59:59");

		return result;
		
	}
	
	@GetMapping("todaytop10")
	public List<Topic> getTodayTop10Topics() {
		DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String now = dtfLog.format(LocalDateTime.now());
		List<Topic> result = topicRepo.getTopicsTop10FromToDate(now + " 00:00:00", now + " 23:59:59");

		return result;
	}
	
	@GetMapping("search/{keyword}")
	public List<Topic> searchTopics(@PathVariable("keyword") String keyword) {
		List<Topic> result = topicRepo.searchKeywords(keyword);
		
		return result;
	}
	
	@GetMapping("exception/{e}")
	public List<Topic> testException(@PathVariable("e") String e) {
		
		try {
			if(e.equals("test")) {
				//throw new TestException("test exception");
				int i=1/0;
			}
		} catch (Exception e2) {
			throw new TestException(e2.toString());
		}

		
		DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String now = dtfLog.format(LocalDateTime.now());
		List<Topic> result = topicRepo.getTopicsFromToDate(now + " 00:00:00", now + " 23:59:59");

		return result;
	}
	
	
	
}
