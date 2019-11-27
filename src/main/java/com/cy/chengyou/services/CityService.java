package com.cy.chengyou.services;

import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.pojos.CityPojo;

import java.util.List;

public interface CityService {

    /**
     * 通过id请求城市信息
     * @param id
     * @return
     */
    CityData findById(String id);

    /**
     * 查询城市信息
     * @param cityPojo
     * @return
     */
    List<CityData> findCity(CityPojo cityPojo);

    /**
     * 从百度地图api获取城市坐标
     */
    void getCityCoordinate();

}
