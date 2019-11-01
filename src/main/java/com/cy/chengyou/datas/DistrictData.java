package com.cy.chengyou.datas;

import com.cy.chengyou.dtos.BaseDto;

public class DistrictData extends BaseDto {

    private String code;
    private String name;
    private String phonetic;
    private String firstPhonetic;
    private Double longitude;
    private Double latitude;
    private String cityCode;
    private CityData cityData;

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public CityData getCityData() {
        return cityData;
    }

    public void setCityData(CityData cityData) {
        this.cityData = cityData;
    }

}
