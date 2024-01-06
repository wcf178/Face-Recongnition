package com.wcf.javaservicer.pojo;

import lombok.Data;

@Data
public class Menu {
    private int id;
    private String path;
    private String component;
    private int parentId;
    private String title;
}
