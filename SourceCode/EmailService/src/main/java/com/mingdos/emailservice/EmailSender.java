package com.mingdos.emailservice;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Component
public class EmailSender {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	Type topicListType = new TypeToken<ArrayList<Topic>>() {}.getType();
	
	public void sendEmail() {
		String content = "";
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		String date = dtf.format(now);
		Gson gson = new Gson();
		final String uri = "http://localhost:8080/WeibotopHistorySpring/todaytop/10";
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
		List<Topic> topicList = gson.fromJson(result, topicListType);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("caimingda1987@gmail.com");
		msg.setSubject("Weibotop History Top 10 for " + date);
		content += "This the today top 10 topics\n\n\n";
		for(int i = 0; i < topicList.size(); ++i) {
			Topic temp = topicList.get(i);
			content += i + 1 + "  " + temp.getHotpoints() + "  " + temp.getTopic() + "\n";
			content += "CONTENT: " + temp.getContent() + "\n\n";
		}
		msg.setText(content);

		javaMailSender.send(msg);
	}

}
