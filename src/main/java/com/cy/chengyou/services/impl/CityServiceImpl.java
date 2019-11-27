package com.cy.chengyou.services.impl;

import com.cy.chengyou.daos.CityDAO;
import com.cy.chengyou.datas.CityData;
import com.cy.chengyou.datas.ProvinceData;
import com.cy.chengyou.dtos.City;
import com.cy.chengyou.dtos.Province;
import com.cy.chengyou.pojos.CityPojo;
import com.cy.chengyou.pojos.baidu.BaiDuResponsePojo;
import com.cy.chengyou.services.CityService;
import com.cy.chengyou.utils.SerializeUtil;
import com.cy.chengyou.utils.StaticConstant;
import com.cy.chengyou.utils.requestutils.GetNetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service(value = "cityService")
public class CityServiceImpl implements CityService {
    @Autowired
    CityDAO cityDAO;

    @Override
    public CityData findById(String id) {
        City city = cityDAO.findByCityId(id);
        CityData cityData = new CityData();
        transformation(city, cityData);
        return cityData;
    }

    @Override
    public List<CityData> findCity(CityPojo cityPojo) {
        List<City> cityList = cityDAO.findCity(cityPojo);
        List<CityData> cityDataList = new ArrayList<>();
        for (City city : cityList) {
            CityData cityData = new CityData();
            transformation(city, cityData);
            cityDataList.add(cityData);
        }
        return cityDataList;
    }

    @Override
    public void getCityCoordinate() {
        CityPojo cityPojo = new CityPojo();
        List<City> cityList = cityDAO.findCity(cityPojo);
        String ak = "";
        for (City city : cityList) {
            if (StringUtils.isEmpty(city.getName())) {
                continue;
            }
            Province province = city.getProvince();
            String cityStr = province.getName() + city.getName() + "政府";
            String URL = "http://api.map.baidu.com/geocoding/v3/?address=" + cityStr + "&output=json&ak=" + ak;
            try {
                String response = GetNetUtil.httpGet(URL, StaticConstant.ENCODING_UTF_8);
                BaiDuResponsePojo baiDuResponsePojo = SerializeUtil.deserializeToJsonByObjectMapper(response, BaiDuResponsePojo.class);
                city.setLatitude(baiDuResponsePojo.getResult().getLocation().getLat());
                city.setLongitude(baiDuResponsePojo.getResult().getLocation().getLng());
                cityDAO.updateCity(city);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 将数据库对象City转换成接口交互对象CityData
     * @param city
     * @param cityData
     */
    public static void transformation(City city, CityData cityData) {
        cityData.setId(city.getId());
        cityData.setCode(city.getCode());
        cityData.setCreateDate(city.getCreateDate());
        cityData.setModifyDate(city.getModifyDate());
        cityData.setPhonetic(city.getPhonetic());
        cityData.setFirstPhonetic(city.getFirstPhonetic());
        cityData.setLongitude(city.getLongitude());
        cityData.setLatitude(city.getLatitude());
        cityData.setName(city.getName());
        cityData.setProvinceCode(city.getProvinceCode());
        ProvinceData provinceData = new ProvinceData();
        ProvinceServiceImpl.transformation(city.getProvince(), provinceData);
        cityData.setProvinceData(provinceData);
    }
}
