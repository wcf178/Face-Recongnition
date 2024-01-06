package com.wcf.javaservicer.utils;

import lombok.Data;

@Data
public class QueryInfo {
    /**
     * 第几页
     */
    private Integer pageNumber;
    /**
     * 每页有几条数据
     */
    private Integer pageSize;
    /**
     * 查询的内容
     * */
    private String queryString;
}
