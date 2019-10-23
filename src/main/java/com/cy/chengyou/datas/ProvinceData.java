package com.cy.chengyou.datas;

import com.cy.chengyou.dtos.BaseDto;

import java.util.List;

public class ProvinceData extends BaseDto {

    private String code;
    private String name;
    private String phonetic;
    private String firstPhonetic;
    private List<CityData> cityDataList;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getFirstPhonetic() {
        return firstPhonetic;
    }

    public void setFirstPhonetic(String firstPhonetic) {
        this.firstPhonetic = firstPhonetic;
    }

    public List<CityData> getCityDataList() {
        return cityDataList;
    }

    public void setCityDataList(List<CityData> cityDataList) {
        this.cityDataList = cityDataList;
    }
}
