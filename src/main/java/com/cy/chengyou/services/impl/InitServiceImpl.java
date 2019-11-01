package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.CityDAO;
import com.cy.chengyou.daos.DistrictDAO;
import com.cy.chengyou.daos.ProvinceDAO;
import com.cy.chengyou.daos.StationDAO;
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

    private StationDAO stationDAO;

    @Override
    public void initData() {

    }
}
