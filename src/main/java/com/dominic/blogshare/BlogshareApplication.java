package com.dominic.blogshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "com.dominic.blogshare.rest")
@EnableSwagger2
public class BlogshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogshareApplication.class, args);
	}

}
