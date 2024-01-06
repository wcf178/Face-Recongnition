package com.wcf.javaservicer.controller;

import com.wcf.javaservicer.pojo.*;
import com.wcf.javaservicer.pojo.Class;
import com.wcf.javaservicer.service.TeachService;
import com.wcf.javaservicer.utils.PageResult;
import com.wcf.javaservicer.utils.QueryPageBean;
import com.wcf.javaservicer.utils.QueryPageBeanForTeach;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.StudentVo;
import com.wcf.javaservicer.vo.WebSubVo;
import com.wcf.javaservicer.vo.WebTeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/teach")
public class TeachController {
    @Autowired
    private  TeachService teachService;
    @RequestMapping("/checkTeacher")
    public PageResult checkTeachers(@RequestBody QueryPageBeanForTeach queryPageBeanForTeach){
        System.out.println(queryPageBeanForTeach.getQueryString());
        PageResult pageResult = teachService.pageQuery(
                queryPageBeanForTeach.getCurrentPage(),
                queryPageBeanForTeach.getPageSize(),
                queryPageBeanForTeach.getQueryString()
                );
//        System.out.println(pageResult.getRows());
        return pageResult;
    }
    @RequestMapping("/checkSubject")
    public PageResult checkSubjects(@RequestBody QueryPageBeanForTeach queryPageBeanForTeach){
        System.out.println(queryPageBeanForTeach.getQueryString());
        PageResult pageResult = teachService.pageQueryForSub(
                queryPageBeanForTeach.getCurrentPage(),
                queryPageBeanForTeach.getPageSize(),
                queryPageBeanForTeach.getQueryString()
        );
//        System.out.println(pageResult.getRows());
        return pageResult;
    }
    @RequestMapping("/deleteTeacher")
    public Result deleteTeacher(Integer teahId){
        System.out.println(teahId);
        try{
            teachService.deleteTeacher(teahId);

        }
        catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }
        catch (Exception e){
            return new Result(false,"删除失败");
        }
        Result result =new Result(true,"删除教师成功");
//        System.out.println("控制类层已被调用"+result.isFlag());
//        Result result = new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        return result;
    }
    @RequestMapping("/deleteSubject")
    public Result deleteSubject(Integer subId){

        try{
            teachService.deleteSubject(subId);

        }
        catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }
        catch (Exception e){
            return new Result(false,"删除失败");
        }
        Result result =new Result(true,"删除教师成功");
//        System.out.println("控制类层已被调用"+result.isFlag());
//        Result result = new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        return result;
    }
    @RequestMapping("/addTeacher")
    public Result addTeacher(@RequestBody Teacher teacher){
        try {
            teacher.setTeahPwd("123456");

            teachService.addTeacher(teacher);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }
        catch (Exception e){
            return new Result(false,"添加教师失败");
        }
        return new Result(true,"添加教师成功");
    }
    @RequestMapping("/addSubject")
    public Result addSubject(@RequestBody Subject subject){
        try {
            subject.toString();
            System.out.println(subject.getSubAcademy());
            teachService.addSubject(subject);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }
        catch (Exception e){
            return new Result(false,"添加学科失败");
        }
        return new Result(true,"添加学科成功");
    }
    @RequestMapping("/checkClass")
    public PageResult checkClass(@RequestBody QueryPageBeanForTeach queryPageBeanForTeach){
        System.out.println(queryPageBeanForTeach.getQueryString());
        PageResult pageResult = teachService.pageQueryForClass(
                queryPageBeanForTeach.getCurrentPage(),
                queryPageBeanForTeach.getPageSize(),
                queryPageBeanForTeach.getQueryString()
        );
//        System.out.println(pageResult.getRows());
        return pageResult;
    }
    @RequestMapping("/deleteStudent")
    public Result deleteStudent( int stuId){
        try{
            teachService.deleteStudentById(stuId);
        }catch (Exception e){
            return new Result(false,e.getMessage());
        }
        return new Result(true,"删除学生成功");
    }
    @RequestMapping("/findAllStudents")
    public Result findAllStudents(){
        List<StudentVo> studentVoList = teachService.findAllStudents();
        return new Result(true,"获取信息成功",studentVoList);
    }
    @RequestMapping("/addClass")
    public Result addClass(@RequestBody TeacherSubject teacherSubject){
        try{
            teachService.addClass(teacherSubject);
            return new Result(true,"添加班级成功");

        }catch (Exception e){
            return  new Result(false,e.getMessage());
        }

    }
    @RequestMapping("/addStudents")
    public void addStudentsForClass(@RequestBody Class class1) throws InterruptedException {
        sleep(1000);
        class1.toString();
        class1.getStudentId().toString();
        teachService.addStudents(class1);


    }
    @RequestMapping("/findAllTeacher")
    public Result findAllTeacher(){
        try{
            List<WebTeacherVo> teachersName=teachService.findAllTeachers();
            return new Result(true,"获取教师信息成功",teachersName);
        }catch (Exception e){
            return new Result(false,e.getMessage());
        }

    }
    @RequestMapping("/findAllSubject")
    public Result findAllSubject(){
        try{
            List<WebSubVo> subjectsName=teachService.findAllSubjects();
            return new Result(true,"获取学科信息成功",subjectsName);
        }catch (Exception e){
            return new Result(false,e.getMessage());
        }

    }

}
