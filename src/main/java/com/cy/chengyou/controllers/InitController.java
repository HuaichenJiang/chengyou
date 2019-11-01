package com.cy.chengyou.controllers;

import com.cy.chengyou.services.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    @Autowired
    private InitService initService;

    @RequestMapping(value = "/initData")
    public void initData() {

    }
}
