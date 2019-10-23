package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.DistrictDAO;
import com.cy.chengyou.services.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictDAO districtDAO;

}
