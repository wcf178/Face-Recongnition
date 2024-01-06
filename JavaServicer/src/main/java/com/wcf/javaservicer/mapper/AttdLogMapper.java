package com.wcf.javaservicer.mapper;

import com.github.pagehelper.Page;
import com.wcf.javaservicer.pojo.Attdlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AttdLogMapper {
    long getAttdIdByOpt(Map params);

    Page<Attdlog> selectByAttdId(Map params);

    Page<Attdlog> selectEarlyByAttdId(long attdId);

    void addAttd(Map params);
    int getClassId(@Param("teahId") int teahId, @Param("subId") int subId);

    List<Integer> findStudentIdsByClassId(int classId);

    void addAttdtoStudent(@Param("stuId") Integer stuId, @Param("attdId") long attdId);

    void addNewLog(@Param("stuId") Integer stuId, @Param("attdId") long attdId,  @Param("stuName")String stuName);

    int getTodayAttdCount(Map params);

    String getStuNameById(Integer stuId);
}
