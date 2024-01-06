package com.wcf.javaservicer.service;

import com.wcf.javaservicer.pojo.Student;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.*;

import java.util.List;

public interface StudentService {
    Result findAll();
    String findFaceById(int stuId);
    void updateFaceById(String stuFace,int stuId);

    int login(WebLoginVo webLoginVo);

    AttdInfoVo getAttd(Integer stuId);

    List<AttdLogVo> getAttdLog(Integer stuId);

     Student getMyInfo(Integer stuId);

    void changePwd(WebLoginVo stuId);

    String analyFaceEncode(String flagName);

    StudentVo compareFace(String faceEncoding, int teahId, int subId);

    Boolean upDateAttdLog(SignVo signVo);
}
