package com.cy.chengyou.services;

import com.cy.chengyou.datas.ProvinceData;
import com.cy.chengyou.pojos.ProvincePojo;

import java.util.List;

public interface ProvinceService {

    /**
     * 通过id查询省份信息
     * @param id
     * @return
     */
    ProvinceData findById(String id);

    /**
     * 查询省份信息
     * @param provincePojo
     * @return
     */
    List<ProvinceData> findProvince(ProvincePojo provincePojo);

}
