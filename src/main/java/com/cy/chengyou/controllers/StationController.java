package com.cy.chengyou.controllers;

import com.cy.chengyou.datas.StationData;
import com.cy.chengyou.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/station")
public class StationController {
    @Autowired
    private StationService stationService;

    @RequestMapping(value = "/findById/{id}")
    public StationData findById(@PathVariable String id) {
        return stationService.findById(id);
    }
}
