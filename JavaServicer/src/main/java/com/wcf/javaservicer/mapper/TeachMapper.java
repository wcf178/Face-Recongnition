package com.wcf.javaservicer.mapper;

import com.github.pagehelper.Page;
import com.wcf.javaservicer.pojo.Subject;
import com.wcf.javaservicer.pojo.Teacher;
import com.wcf.javaservicer.pojo.TeacherSubject;
import com.wcf.javaservicer.vo.ClassVo;
import com.wcf.javaservicer.vo.StudentVo;
import com.wcf.javaservicer.vo.WebSubVo;
import com.wcf.javaservicer.vo.WebTeacherVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeachMapper {
    Page<Teacher> selectTeachersByName(String queryString);

    Page<Subject> selectSubjectsByName(String queryString);

    void deleteTeacherById(Integer teahId);

    void deleteTeahSubById(Integer teahId);

    void deleteSubjectById(Integer subId);

    void deleteSubTeahById(Integer subId);

    Integer findTeacherBySubId(Integer subId);

    int findTeacherById(int teahId);

    void addTeacher(Teacher teacher);

    int findSubjectById(int subId);

    int findSubjectByName(String subName);

    void addSubject(Subject subject);

    Page<ClassVo> selectClassVo(int classId);

    int getSubIdByClassId(int classId);

    void deleteStudentById(int stuId);

    List<StudentVo> findAllStudents();

    void addClass(TeacherSubject teacherSubject);

    void addStudents(@Param("classId") int classId, @Param("stuId") Integer stuId);

    List<WebTeacherVo> findAllTeachers();
    List<WebSubVo> findAllSubjects();

    int findClassId(@Param("teahId") int teahId, @Param("subId") int subId);

    int IsClassIdExsit(int classId);


    List<Integer> getClassIdByteahId(Integer teahId);

    void deletestudentByClassId(Integer classId);
}
