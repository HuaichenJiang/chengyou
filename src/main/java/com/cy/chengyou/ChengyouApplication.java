package com.cy.chengyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("com.cy.chengyou.daos")
@SpringBootApplication
public class ChengyouApplication {

	public static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(ChengyouApplication.class, args);
	}

}
