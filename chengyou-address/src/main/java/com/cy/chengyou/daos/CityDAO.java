package com.cy.chengyou.daos;

import com.cy.chengyou.dtos.City;
import com.cy.chengyou.pojos.CityPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDAO {

    City findByCityId(String id);

    List<City> findCity(CityPojo cityPojo);

    Integer insertCity(City city);

    Integer updateCity(City city);

    Integer deleteCity(City City);
}
