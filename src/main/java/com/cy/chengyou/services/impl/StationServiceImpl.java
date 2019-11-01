package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.StationDAO;
import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.datas.DistrictData;
import com.cy.chengyou.datas.StationData;
import com.cy.chengyou.dtos.Station;
import com.cy.chengyou.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDAO stationDAO;

    @Override
    public StationData findById(String id) {
        Station station = stationDAO.findByStaId(id);
        StationData stationData = new StationData();
        transformation(station, stationData);
        return stationData;
    }

    public static void transformation(Station station, StationData stationData){
        stationData.setId(station.getId());
        stationData.setCreateDate(station.getCreateDate());
        stationData.setModifyDate(station.getModifyDate());
        stationData.setCode(station.getCode());
        stationData.setName(station.getName());
        stationData.setPhonetic(station.getPhonetic());
        stationData.setFirstPhonetic(station.getFirstPhonetic());
        stationData.setLatitude(station.getLatitude());
        stationData.setLongitude(station.getLongitude());
        stationData.setIntercity(station.getIntercity());
        if (!StringUtils.isEmpty(station.getDistrictCode())) {
            stationData.setDistrictCode(station.getDistrictCode());
            DistrictData districtData = new DistrictData();
            DistrictServiceImpl.transformation(station.getDistrict(), districtData);
            stationData.setDistrictData(districtData);
        } else if (!StringUtils.isEmpty(station.getCityCode())) {
            stationData.setCityCode(station.getCityCode());
            CityData cityData = new CityData();
            CityServiceImpl.transformation(station.getCity(), cityData);
            stationData.setCityData(cityData);
        }
    }
}
