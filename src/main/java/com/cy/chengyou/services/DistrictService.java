package com.cy.chengyou.services;

import com.cy.chengyou.datas.DistrictData;

public interface DistrictService {

    /**
     * 查询区县信息
     * @param id
     * @return
     */
    DistrictData findById(String id);

}
