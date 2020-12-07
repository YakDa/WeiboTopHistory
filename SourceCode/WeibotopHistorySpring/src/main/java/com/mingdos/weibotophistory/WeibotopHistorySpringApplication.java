package com.mingdos.weibotophistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableAsync
public class WeibotopHistorySpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeibotopHistorySpringApplication.class, args);
	}

}
