package com.example.zuul.simpleRouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class SimpleRouterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRouterApplication.class, args);
	}
}
