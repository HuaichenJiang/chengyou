package com.cy.chengyou.services;

import com.cy.chengyou.datas.StationData;

public interface StationService {

    /**
     * 通过id查询火车站点信息
     * @param id
     * @return
     */
    StationData findById(String id);

}
