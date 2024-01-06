package com.wcf.javaservicer.mapper;

import com.wcf.javaservicer.pojo.Student;
import com.wcf.javaservicer.vo.*;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface StudentMapper {
    /*查找所有学生信息*/
    List<Student> findAll();
    /*根据学生ID查找face*/
    String findFaceById(int stuId);
    void updateFaceById(Map params);

    int findStuById(int userId);

    String getPwdById(int userId);

    long getAttdId(Integer stuId);

    Date getAttdDateById(long attdId);

    AttdInfoVo getAttdInfoById(long attdId);

    int getAttdLogCount(Integer stuId);

    List<AttdLogVo> getAttdLogsById(Integer stuId);

    Student getMyInfo(Integer stuId);

    void updatePwdById(@Param("stuId") Integer stuId, @Param("stuPwd") String stuPwd);

    List<StudentFaceVo> getAllFaceCodes(int classId);

    void updateAttdLogIn(Map signVo);

    void updateAttdLogOut(Map signVo);

    int findClass(@Param("teahId") int teahId, @Param("subId") int subId);
}
