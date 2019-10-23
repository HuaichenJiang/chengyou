package com.cy.chengyou.services;

import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.pojos.CityPojo;

import java.util.List;

public interface CityService {

    CityData findById(String id);

    List<CityData> findCity(CityPojo cityPojo);

}
