package com.wcf.javaservicer.controller;

import com.wcf.javaservicer.pojo.Subject;
import com.wcf.javaservicer.service.TeacherService;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.WebLoginVo;
import com.wcf.javaservicer.vo.WebSubVo;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class WebLoginController {
    @Autowired
    private TeacherService teacherService;
    @PostMapping(value = "/login")
    public Result login(@RequestBody WebLoginVo webLoginVo){
        System.out.println(webLoginVo.getUserId());

        return teacherService.login(webLoginVo);
    }

    @RequestMapping(value="/info")
    public Result getInfo(int teahId){
        System.out.println(teahId);
        return teacherService.getInfoById(teahId);
    }
    @RequestMapping(value = "/checkSub")
    public Result getSubById(int teahId){
        List<WebSubVo> subjectList = teacherService.getSubListById(teahId);
        return new Result(true,"获取学科信息成功",subjectList);
    }
}
