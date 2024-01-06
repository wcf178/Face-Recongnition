package com.wcf.javaservicer.service;

import com.wcf.javaservicer.pojo.Subject;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.WebLoginVo;
import com.wcf.javaservicer.vo.WebSubVo;

import java.util.List;

public interface TeacherService {
    Result findAll();
    Result login(WebLoginVo webLoginVo);

    Result getInfoById(int teahId);

    List<WebSubVo> getSubListById(int teahId);
}
