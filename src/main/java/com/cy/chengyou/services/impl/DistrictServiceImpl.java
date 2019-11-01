package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.DistrictDAO;
import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.datas.DistrictData;
import com.cy.chengyou.dtos.District;
import com.cy.chengyou.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictDAO districtDAO;

    @Override
    public DistrictData findById(String id) {
        District district = districtDAO.findDisById(id);
        DistrictData districtData = new DistrictData();
        transformation(district, districtData);
        return null;
    }

    public static void transformation(District district, DistrictData districtData) {
        districtData.setId(district.getId());
        districtData.setCreateDate(district.getCreateDate());
        districtData.setModifyDate(district.getModifyDate());
        districtData.setCode(district.getCode());
        districtData.setName(district.getName());
        districtData.setPhonetic(district.getPhonetic());
        districtData.setFirstPhonetic(district.getFirstPhonetic());
        districtData.setLatitude(district.getLatitude());
        districtData.setLongitude(district.getLongitude());
        districtData.setCityCode(district.getCityCode());
        CityData cityData = new CityData();
        CityServiceImpl.transformation(district.getCity(), cityData);
        districtData.setCityData(cityData);
    }
}
