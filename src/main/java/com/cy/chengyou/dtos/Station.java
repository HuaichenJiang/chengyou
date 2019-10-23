package com.cy.chengyou.dtos;

public class Station extends BaseDto {

    private String code;
    private String name;
    private String phonetic;
    private String firstPhonetic;
    private Boolean isIntercity;
    private Double longitude;
    private Double latitude;
    private String cityCode;
    private String districtCode;
    private City city;
    private District district;

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

    public Boolean getIntercity() {
        return isIntercity;
    }

    public void setIntercity(Boolean intercity) {
        isIntercity = intercity;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
