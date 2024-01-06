package com.wcf.javaservicer.service;


import com.wcf.javaservicer.utils.PageResult;
import com.wcf.javaservicer.vo.AttdQueryVo;

import java.math.BigDecimal;

public interface AttdService {
    long getAttdIdByOpt(AttdQueryVo attdQueryVo);

    PageResult pageQuery(Integer currentPage, Integer pageSize, long queryString, int queryState);

    void addAttd(int teahId, int subId, String attdDate, String startTime, String endTime, BigDecimal latitude, BigDecimal longitude, Integer distance);
}
