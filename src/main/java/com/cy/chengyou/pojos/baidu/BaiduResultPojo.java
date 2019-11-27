package com.cy.chengyou.pojos.baidu;

public class BaiduResultPojo {

    // 经纬度
    private BaiduLocationPojo location;
    // 是否精确打点，1为精确，0为模糊
    private Integer precise;
    // 打点绝对精度
    private Integer confidence;
    // 地址理解程度
    private Integer comprehension;
    // 地址类型
    private String level;

    public BaiduLocationPojo getLocation() {
        return location;
    }

    public void setLocation(BaiduLocationPojo location) {
        this.location = location;
    }

    public Integer getPrecise() {
        return precise;
    }

    public void setPrecise(Integer precise) {
        this.precise = precise;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Integer getComprehension() {
        return comprehension;
    }

    public void setComprehension(Integer comprehension) {
        this.comprehension = comprehension;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
