package com.wcf.javaservicer.pojo;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Attdlog {
    private long attdId;
    private int stuId;
    private String stuName;
    private Timestamp attdTime;
    private int attdFlag;
    private Timestamp leaveTime;
    private int leaveFlag;
}
