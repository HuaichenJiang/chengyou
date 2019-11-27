package com.cy.chengyou.pojos.baidu;

public class BaiDuResponsePojo {
    // 结果状态值
    private Integer status;
    // 解析结果
    private BaiduResultPojo result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BaiduResultPojo getResult() {
        return result;
    }

    public void setResult(BaiduResultPojo result) {
        this.result = result;
    }
}
