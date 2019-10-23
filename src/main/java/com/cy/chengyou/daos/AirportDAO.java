package com.cy.chengyou.daos;

import com.cy.chengyou.dtos.Airport;
import com.cy.chengyou.pojos.AirportPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDAO {

    Airport findByAirId(String id);

    List<Airport> findAirport(AirportPojo airportPojo);

}
