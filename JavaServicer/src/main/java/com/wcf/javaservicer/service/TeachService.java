package com.wcf.javaservicer.service;

import com.wcf.javaservicer.pojo.Class;
import com.wcf.javaservicer.pojo.Subject;
import com.wcf.javaservicer.pojo.Teacher;
import com.wcf.javaservicer.pojo.TeacherSubject;
import com.wcf.javaservicer.utils.PageResult;
import com.wcf.javaservicer.vo.StudentVo;
import com.wcf.javaservicer.vo.WebSubVo;
import com.wcf.javaservicer.vo.WebTeacherVo;

import java.util.List;

public interface TeachService {
    PageResult pageQuery(Integer currentPage, Integer pageSize,String queryString);

    PageResult pageQueryForSub(Integer currentPage, Integer pageSize, String queryString);

    void deleteTeacher(Integer teahId);

    void deleteSubject(Integer subId);

    void addTeacher(Teacher teacher);

    void addSubject(Subject subject);

    PageResult pageQueryForClass(Integer currentPage, Integer pageSize, String queryString);

    void deleteStudentById(int stuId);

    List<StudentVo> findAllStudents();

    void addClass(TeacherSubject teacherSubject);

    void addStudents(Class class1);

    List<WebTeacherVo> findAllTeachers();
    List<WebSubVo> findAllSubjects();
}
