package com.wcf.javaservicer.vo;

import lombok.Data;

@Data
public class AttdLogVo {
    /*早期数据库设计不合理，导致出现了许多VO类，我也不想啊*/
    private String attdDate;
    private String teahName;
    private String subName;
    private String startTime;
    private String endTime;
    private String attdTime;
    private int attdFlag;
    private String leaveTime;
    private int leaveFlag;
}
