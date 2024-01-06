package com.wcf.javaservicer.controller;

import com.wcf.javaservicer.service.StudentService;
import com.wcf.javaservicer.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {
    @Autowired
    private StudentService studentService;
    @PostMapping("/test")
    public Result  test(){
        System.out.println("hello||!");
        return studentService.findAll();
    }
}

