package com.cy.chengyou.controllers;

import com.cy.chengyou.datas.DistrictData;
import com.cy.chengyou.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @RequestMapping(value = "/findById/{id}")
    public DistrictData findById(@PathVariable String id) {
        return districtService.findById(id);
    }

}
