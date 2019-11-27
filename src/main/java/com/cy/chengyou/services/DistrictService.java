package com.cy.chengyou.services;

import com.cy.chengyou.datas.DistrictData;
import com.cy.chengyou.pojos.DistrictPojo;

import java.util.List;

public interface DistrictService {

    /**
     * 查询区县信息
     * @param id
     * @return
     */
    DistrictData findById(String id);

    List<DistrictData> findDistrict(DistrictPojo districtPojo);

    void getDistrictCoordinate();

}
