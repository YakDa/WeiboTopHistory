package com.mingdos.emailservice;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EmailServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EmailServiceApplication.class, args);
		
		EmailSender sender = context.getBean(EmailSender.class);
		
		while(true) {
			
			sender.sendEmail();
			try {
				TimeUnit.SECONDS.sleep(86400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		

}
