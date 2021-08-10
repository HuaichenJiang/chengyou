package com.cy.chengyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Huaichen.Jiang
 * @description
 * @date 2020/5/23 9:49 AM
 */
@SpringBootApplication
@MapperScan("com.cy.chengyou.daos")
public class ChengYouAddressApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChengYouAddressApplication.class, args);
    }
}
