package com.cy.chengyou.daos;

import com.cy.chengyou.dtos.District;
import com.cy.chengyou.pojos.DistrictPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictDAO {

    District findDisById(String id);

    List<District> findDistrict(DistrictPojo districtPojo);

    Integer insertDistrict(District district);

    Integer updateDistrict(District district);

    Integer deleteDistrict(District district);

}
