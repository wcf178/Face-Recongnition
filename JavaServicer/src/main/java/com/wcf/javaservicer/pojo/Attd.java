package com.wcf.javaservicer.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Attd {
    private int teahId;
    private int subId;
    private String attdDate;
    private String startTime;
    private String endTime;
    private long attdId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer distance;

}
