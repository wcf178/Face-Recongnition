package com.wcf.javaservicer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

@MapperScan("com.wcf.javaservicer.mapper")
@SpringBootApplication
public class JavaServicerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaServicerApplication.class, args);
    }

}
