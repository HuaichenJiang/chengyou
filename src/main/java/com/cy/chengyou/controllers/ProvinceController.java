package com.cy.chengyou.controllers;

import com.cy.chengyou.datas.ProvinceData;
import com.cy.chengyou.pojos.ProvincePojo;
import com.cy.chengyou.services.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/province")
public class ProvinceController {
    @Autowired
    ProvinceService provinceService;
    private static final Logger LOG = LoggerFactory.getLogger(ProvinceController.class);

    /**
     * 通过id查询省份信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById/{id}")
    public ProvinceData findById(@PathVariable String id) {
        return provinceService.findById(id);
    }

    /**
     * 查询省份信息
     * @param provincePojo
     * @return
     */
    @RequestMapping(value = "/findProvince")
    public List<ProvinceData> findProvince(@RequestBody ProvincePojo provincePojo) {
        return provinceService.findProvince(provincePojo);
    }

}
