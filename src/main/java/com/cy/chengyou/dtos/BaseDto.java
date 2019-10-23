package com.cy.chengyou.dtos;

import java.util.Date;

public class BaseDto {

    Integer id;
    Date createDate;
    Date modifyDate;

    public BaseDto() {
        createDate = new Date();
        modifyDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
