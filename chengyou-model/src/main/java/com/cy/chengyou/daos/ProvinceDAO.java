package com.cy.chengyou.daos;

import com.cy.chengyou.dtos.Province;
import com.cy.chengyou.pojos.ProvincePojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceDAO {

    Province findByProId(String id);

    List<Province> findProvince(ProvincePojo provincePojo);

    Integer insertProvince(Province province);

    Integer updateProvince(Province province);

    Integer deleteProvince(Province province);
}
