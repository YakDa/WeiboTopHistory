package com.mingdos.weibotophistory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mingdos.weibotophistory.model.Topic;
import com.mingdos.weibotophistory.repository.TopicRepo;

@Controller
public class TopicController {

	@Autowired
	TopicRepo topicRepo;
	
	@GetMapping("/")
	public String home(Model m) {
		
		DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String now = dtfLog.format(LocalDateTime.now());
		List<Topic> result = topicRepo.getTopicsFromToDate(now + " 00:00:00", now + " 23:59:59");
		m.addAttribute("topics", result);
		return "home";
	}
	
	@GetMapping("top")
	public String getTopTopics(@RequestParam("toptype")String topType, Model m) {
		
		if (topType.equals("today")) {
			DateTimeFormatter dtfLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String now = dtfLog.format(LocalDateTime.now());
			List<Topic> result = topicRepo.getTopicsFromToDate(now + " 00:00:00", now + " 23:59:59");
			m.addAttribute("topics", result);
		}
		else if (topType.equals("traffic")) {
			List<Topic> result = topicRepo.getTopicsBasedTraffic(50);
			m.addAttribute("topics", result);
		}
		else if (topType.equals("duration")) {
			List<Topic> result = topicRepo.getTopicsBasedDuration(50);
			m.addAttribute("topics", result);
		}
		return "home";
		
	}
	
	@GetMapping("showinfo")
	public String showTopicInfo(@RequestParam("id")String id, Model m) {
		
		Topic result = topicRepo.findById(id).orElse(null);
		m.addAttribute("topic", result);
		return "topicinfo";
	}
	
	@GetMapping("selecttopics")
	public String selectTopics(@RequestParam("from") String from, @RequestParam("to") String to, Model m) {
		
		List<Topic> result = topicRepo.getTopicsFromToDate(from + " 00:00:00", to + " 23:59:59");
		m.addAttribute("topics", result);
		return "findtopic";
	}
}
