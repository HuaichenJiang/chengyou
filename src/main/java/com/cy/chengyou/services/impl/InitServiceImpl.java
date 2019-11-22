package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.*;
import com.cy.chengyou.services.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {
    @Autowired
    private ProvinceDAO provinceDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private DistrictDAO districtDAO;
    @Autowired
    private StationDAO stationDAO;
    @Autowired
    private AirportDAO airportDAO;

    @Override
    public void initData() {

    }
}
