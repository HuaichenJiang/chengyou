package com.cy.chengyou.controllers;

import com.cy.chengyou.datas.DistrictData;
import com.cy.chengyou.pojos.DistrictPojo;
import com.cy.chengyou.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    /**
     * 查询区县信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById/{id}")
    public DistrictData findById(@PathVariable String id) {
        return districtService.findById(id);
    }

    /**
     * 查询区县信息
     * @param districtPojo
     * @return
     */
    @RequestMapping(value = "/findDistrict")
    public List<DistrictData> findDistrict(DistrictPojo districtPojo){
        return districtService.findDistrict(districtPojo);
    }

    /**
     * 更新区县经纬度
     * @return
     */
    @RequestMapping(value = "/getDistrictCoordinate")
    public String getDistrictCoordinate() {
        districtService.getDistrictCoordinate();
        return "success";
    }

}
