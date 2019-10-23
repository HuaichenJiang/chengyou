package com.cy.chengyou.services;

import com.cy.chengyou.datas.ProvinceData;
import com.cy.chengyou.pojos.ProvincePojo;

import java.util.List;

public interface ProvinceService {

    ProvinceData findById(String id);

    List<ProvinceData> findProvince(ProvincePojo provincePojo);

}
