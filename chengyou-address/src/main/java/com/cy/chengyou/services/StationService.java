package com.cy.chengyou.services;

import com.cy.chengyou.datas.StationData;
import com.cy.chengyou.pojos.StationPojo;

import java.util.List;

public interface StationService {

    /**
     * 通过id查询火车站点信息
     * @param id
     * @return
     */
    StationData findById(String id);

    /**
     * 查询站点
     * @param stationPojo
     * @return
     */
    List<StationData> findStation(StationPojo stationPojo);

}
