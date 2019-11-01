package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.CityDAO;
import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.datas.ProvinceData;
import com.cy.chengyou.dtos.City;
import com.cy.chengyou.pojos.CityPojo;
import com.cy.chengyou.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "cityService")
public class CityServiceImpl implements CityService {
    @Autowired
    CityDAO cityDAO;

    @Override
    public CityData findById(String id) {
        City city = cityDAO.findByCityId(id);
        CityData cityData = new CityData();
        transformation(city, cityData);
        return cityData;
    }

    @Override
    public List<CityData> findCity(CityPojo cityPojo) {
        List<City> cityList = cityDAO.findCity(cityPojo);
        List<CityData> cityDataList = new ArrayList<>();
        for (City city : cityList) {
            CityData cityData = new CityData();
            transformation(city, cityData);
            cityDataList.add(cityData);
        }
        return cityDataList;
    }

    public static void transformation(City city, CityData cityData) {
        cityData.setId(city.getId());
        cityData.setCode(city.getCode());
        cityData.setCreateDate(city.getCreateDate());
        cityData.setModifyDate(city.getModifyDate());
        cityData.setPhonetic(city.getPhonetic());
        cityData.setFirstPhonetic(city.getFirstPhonetic());
        cityData.setName(city.getName());
        ProvinceData provinceData = new ProvinceData();
        ProvinceServiceImpl.transformation(city.getProvince(), provinceData);
        cityData.setProvinceData(provinceData);
    }
}
