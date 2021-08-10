package com.cy.chengyou.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(value = {"handler"})
public class City extends BaseDto {

    private String code;
    private String name;
    private String phonetic;
    private String firstPhonetic;
    private Double longitude;
    private Double latitude;
    private String provinceCode;
    private Province province;
    private List<District> districtList;
    private List<Station> stationList;
    private List<Airport> airportList;

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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void setStationList(List<Station> stationList) {
        this.stationList = stationList;
    }

    public List<Airport> getAirportList() {
        return airportList;
    }

    public void setAirportList(List<Airport> airportList) {
        this.airportList = airportList;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
