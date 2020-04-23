package com.cy.chengyou.datas;

public class PageData {
    // 分页大小
    private Integer pageSize;
    // 当前页数
    private Integer pageLoad;
    // 总页数
    private Integer totalPage;
    // 总数量
    private Integer totalSize;
    // 数据集
    private Object result;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageLoad() {
        return pageLoad;
    }

    public void setPageLoad(Integer pageLoad) {
        this.pageLoad = pageLoad;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
