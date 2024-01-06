package com.wcf.javaservicer.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wcf.javaservicer.mapper.TeachMapper;
import com.wcf.javaservicer.mapper.TeacherMapper;
import com.wcf.javaservicer.pojo.Attdlog;
import com.wcf.javaservicer.pojo.Class;
import com.wcf.javaservicer.pojo.Subject;
import com.wcf.javaservicer.pojo.Teacher;
import com.wcf.javaservicer.pojo.TeacherSubject;
import com.wcf.javaservicer.service.TeachService;
import com.wcf.javaservicer.utils.PageResult;
import com.wcf.javaservicer.vo.ClassVo;
import com.wcf.javaservicer.vo.StudentVo;
import com.wcf.javaservicer.vo.WebSubVo;
import com.wcf.javaservicer.vo.WebTeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeachServiceImpl implements TeachService {
    @Autowired
    private TeachMapper teachMapper;
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Teacher> page = teachMapper.selectTeachersByName(queryString);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public PageResult pageQueryForSub(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Subject> page = teachMapper.selectSubjectsByName(queryString);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void deleteTeacher(Integer teahId) {
        teachMapper.deleteTeacherById(teahId);
        List<Integer> classIdList = teachMapper.getClassIdByteahId(teahId);
        teachMapper.deleteTeahSubById(teahId);
        for (Integer classId:
                classIdList) {
            teachMapper.deletestudentByClassId(classId);
        }

    }

    @Override
    public void deleteSubject(Integer subId) {
        Integer count = teachMapper.findTeacherBySubId(subId);
        if(count > 0){
            throw new RuntimeException("当前学科被引用，不能删除");

        }
        teachMapper.deleteSubjectById(subId);


    }

    @Override
    @Transient
    public void addTeacher(Teacher teacher) {
        int count = teachMapper.findTeacherById(teacher.getTeahId());
        if(count>0){
            throw new RuntimeException("教师ID重复，请确认信息");
        }
        else{
            teachMapper.addTeacher(teacher);
        }
    }

    @Override
    @Transient
    public void addSubject(Subject subject) {
        int countSub = teachMapper.findSubjectById(subject.getSubId());

        if(countSub>0){
            throw new RuntimeException("学科ID重复，请确认信息");
        }
        else {
            countSub += teachMapper.findSubjectByName(subject.getSubName());
            if(countSub>0){
                throw new RuntimeException("学科名称重复，请确认信息");
            }
            teachMapper.addSubject(subject);
        }
    }

    @Override
    public PageResult pageQueryForClass(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        int classId = Integer.valueOf(queryString);
        //int subId = teachMapper.getSubIdByClassId(classId);

        Page<ClassVo> page = teachMapper.selectClassVo(classId);
        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void deleteStudentById(int stuId) {
        teachMapper.deleteStudentById(stuId);
    }

    @Override
    public List<StudentVo> findAllStudents() {

        return teachMapper.findAllStudents();
    }

    @Override
    public void addClass(TeacherSubject teacherSubject) {
        int count = teachMapper.findClassId(teacherSubject.getTeahId(),teacherSubject.getSubId());
        if(count > 0){
            throw new RuntimeException("对应学科教师班级已存在，请修改信息");
        }
        int classIdcount = teachMapper.IsClassIdExsit(teacherSubject.getClassId());
        if(classIdcount > 0){
            throw new RuntimeException("班级编号已存在，请修改信息");
        }
        teachMapper.addClass(teacherSubject);
    }

    @Override
    public void addStudents(Class class1) {
        //class1.setCheckGroupId(this.checkGroupDao.getGroupIdByCode(groupToItem.getCode()));
        for (Integer stuId:
                class1.getStudentId()) {
            //this.checkGroupDao.add_m(groupToItem.getCheckGroupId(),itemid);
            teachMapper.addStudents(class1.getClassId(),stuId);
        }
    }

    @Override
    public List<WebTeacherVo> findAllTeachers() {
        List<WebTeacherVo> teacherList = teachMapper.findAllTeachers();
        return teacherList;
    }
    @Override
    public List<WebSubVo> findAllSubjects() {
        List<WebSubVo> subList = teachMapper.findAllSubjects();
        return subList;
    }
}
