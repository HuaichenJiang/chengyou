package com.cy.chengyou.daos;

import com.cy.chengyou.dtos.Station;
import com.cy.chengyou.pojos.StationPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationDAO {

    Station findByStaId(String id);

    List<Station> findStation(StationPojo stationPojo);

    Integer insertStation(Station station);

}
