package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.DistrictDAO;
import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.datas.DistrictData;
import com.cy.chengyou.dtos.City;
import com.cy.chengyou.dtos.District;
import com.cy.chengyou.dtos.Province;
import com.cy.chengyou.pojos.DistrictPojo;
import com.cy.chengyou.pojos.baidu.BaiDuResponsePojo;
import com.cy.chengyou.services.DistrictService;
import com.cy.chengyou.utils.SerializeUtil;
import com.cy.chengyou.utils.StaticConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictDAO districtDAO;

    @Override
    public DistrictData findById(String id) {
        District district = districtDAO.findDisById(id);
        DistrictData districtData = new DistrictData();
        transformation(district, districtData);
        return districtData;
    }

    @Override
    public List<DistrictData> findDistrict(DistrictPojo districtPojo) {
        return null;
    }

    @Override
    public void getDistrictCoordinate() {
        DistrictPojo districtPojo = new DistrictPojo();
        List<District> districtList = districtDAO.findDistrict(districtPojo);
        String ak = "";
        for (District district : districtList) {
            City city = district.getCity();
            Province province = city.getProvince();
            String cityStr = "";
            if (StringUtils.isEmpty(city.getName())) {
                cityStr = province.getName() + district.getName() + "政府";
            } else {
                cityStr = province.getName() + city.getName() + district.getName() +"政府";
            }
            String URL = "http://api.map.baidu.com/geocoding/v3/?address=" + cityStr + "&output=json&ak=" + ak;
            try {
                String response = null;
//                        GetNetUtil.httpGet(URL, StaticConstant.ENCODING_UTF_8);
                BaiDuResponsePojo baiDuResponsePojo = SerializeUtil.deserializeToJsonByObjectMapper(response, BaiDuResponsePojo.class);
                district.setLatitude(baiDuResponsePojo.getResult().getLocation().getLat());
                district.setLongitude(baiDuResponsePojo.getResult().getLocation().getLng());
                districtDAO.updateDistrict(district);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 将数据库区县对象District转换成接口交互对象DistrictData
     * @param district
     * @param districtData
     */
    public static void transformation(District district, DistrictData districtData) {
        districtData.setId(district.getId());
        districtData.setCreateDate(district.getCreateDate());
        districtData.setModifyDate(district.getModifyDate());
        districtData.setCode(district.getCode());
        districtData.setName(district.getName());
        districtData.setPhonetic(district.getPhonetic());
        districtData.setFirstPhonetic(district.getFirstPhonetic());
        districtData.setLatitude(district.getLatitude());
        districtData.setLongitude(district.getLongitude());
        districtData.setCityCode(district.getCityCode());
        CityData cityData = new CityData();
        CityServiceImpl.transformation(district.getCity(), cityData);
        districtData.setCityData(cityData);
    }
}
