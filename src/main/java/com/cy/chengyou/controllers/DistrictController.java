package com.cy.chengyou.controllers;

import com.cy.chengyou.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

}
