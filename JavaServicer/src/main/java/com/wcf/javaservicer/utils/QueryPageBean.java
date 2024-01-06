package com.wcf.javaservicer.utils;

import java.io.Serializable;

public class QueryPageBean implements Serializable {

    private Integer currentPage;
    private Integer pageSize;
    private long queryString;
    private int queryState;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public long getQueryString() {
        return queryString;
    }

    public void setQueryString(long queryString) {
        this.queryString = queryString;
    }

    public int getQueryState() {
        return queryState;
    }

    public void setQueryState(int queryState) {
        this.queryState = queryState;
    }

    @Override
    public String toString() {
        return "QueryPageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", queryString='" + queryString + '\'' +
                '}';
    }
}
