package com.wcf.javaservicer.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttdInfoVo {
    private String attdId;
    private int teahId;
    private int subId;
    private String teahName;
    private String subName;
    private String startTime;
    private String endTime;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer distance;


}
