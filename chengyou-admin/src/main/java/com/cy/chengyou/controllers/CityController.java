package com.cy.chengyou.controllers;

import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.pojos.CityPojo;
import com.cy.chengyou.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 通过id请求城市信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById/{id}")
    public CityData findById(@PathVariable String id) {
        return cityService.findById(id);
    }

    /**
     * 查询城市信息
     * @param cityPojo
     * @return
     */
    @RequestMapping(value = "/findCity")
    public List<CityData> findCity(@RequestBody CityPojo cityPojo) {
        return cityService.findCity(cityPojo);
    }

    /**
     * 更新城市经纬度
     * @return
     */
    @RequestMapping(value = "/getCityCoordinate")
    public String getCityCoordinate() {
        cityService.getCityCoordinate();
        return "success";
    }

}
