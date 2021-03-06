package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.ProvinceDAO;
import com.cy.chengyou.datas.ProvinceData;
import com.cy.chengyou.dtos.Province;
import com.cy.chengyou.pojos.ProvincePojo;
import com.cy.chengyou.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "provinceService")
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceDAO provinceDAO;

    @Override
    public ProvinceData findById(String id) {
        Province province = provinceDAO.findByProId(id);
        ProvinceData provinceData = new ProvinceData();
        transformation(province, provinceData);
        return provinceData;
    }

    @Override
    public List<ProvinceData> findProvince(ProvincePojo provincePojo) {
        List<Province> provinceList = provinceDAO.findProvince(provincePojo);
        List<ProvinceData> provinceDataList = new ArrayList<>();
        for (Province province : provinceList) {
            ProvinceData provinceData = new ProvinceData();
            transformation(province, provinceData);
            provinceDataList.add(provinceData);
        }
        return provinceDataList;
    }

    /**
     * 将数据库对象Province转换成接口交互对象ProvinceData
     * @param province
     * @param provinceData
     */
    public static void transformation(Province province, ProvinceData provinceData) {
        provinceData.setId(province.getId());
        provinceData.setCode(province.getCode());
        provinceData.setCreateDate(province.getCreateDate());
        provinceData.setModifyDate(province.getModifyDate());
        provinceData.setPhonetic(province.getPhonetic());
        provinceData.setFirstPhonetic(province.getFirstPhonetic());
        provinceData.setName(province.getName());
    }

}
