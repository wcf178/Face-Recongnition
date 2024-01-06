package com.wcf.javaservicer.mapper;


import com.wcf.javaservicer.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {
    List<Teacher> findAll();

    String getPwdById(int userId);

    Teacher findInfoById(int teahId);

    List<Integer> findSubsById(int teahId);

    String getPermission(int userId);
}
