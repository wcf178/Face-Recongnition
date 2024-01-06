package com.wcf.javaservicer.service.impl;

import com.wcf.javaservicer.config.ApplicationValues;
import com.wcf.javaservicer.mapper.StudentMapper;
import com.wcf.javaservicer.pojo.Student;
import com.wcf.javaservicer.service.StudentService;
import com.wcf.javaservicer.utils.NetworkUtil;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ApplicationValues appvalue;
    @Override
    public Result findAll() {
        log.info("获取学生信息");
        List<Student> studentList = studentMapper.findAll();
        for(Student stu:studentList){

            System.out.println(stu.getStuName());
        }
        return Result.success("获取学生信息成功",studentList);

    }

    @Override
    public String findFaceById(int stuId) {
        log.info("获取指定学生人脸信息");
        return studentMapper.findFaceById(stuId);
    }

    @Override
    public void updateFaceById(String stuFace ,int stuId) {
        log.info("根据id插入学生的人脸特征");
        Map params = new HashMap<>();
        params.put("stuFace",stuFace);
        params.put("stuId",stuId);
        studentMapper.updateFaceById(params);
    }

    @Override
    public int login(WebLoginVo webLoginVo) {
        int count = studentMapper.findStuById(webLoginVo.getUserId());
        if(count > 0) {
            String pwd = studentMapper.getPwdById(webLoginVo.getUserId());
            String face = studentMapper.findFaceById(webLoginVo.getUserId());
            if (webLoginVo.getPassword().equals(pwd)) {
                if(face == null) {
                    return 2;
                }
                else{
                    return 1;
                }

            } else {
                return 0;
            }
        }
        else
            return -1;
    }

    @Override
    public AttdInfoVo getAttd(Integer stuId) {
        long attdId = studentMapper.getAttdId(stuId);
        /*
            首先查看学生信息表中考勤ID是否被更新写入
            若没有直接返回错误信息
            若有则对对应的考勤ID执行查询时间的操作
            只有当天的考勤ID可以被用于查询
        * */
        if(attdId == 0L){
            throw new RuntimeException("该学生暂时没有考勤Id");
        }
        else{

            //又到了最烦人的时间对比时刻
            Date nowDate = new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
            System.out.println(dateFormat.format(nowDate));
            System.out.println (studentMapper.getAttdDateById(attdId).getClass ().getName ());
            java.sql.Date attdDate = studentMapper.getAttdDateById(attdId);
            //Date attdDate = studentMapper.getAttdDateById(attdId);
            System.out.println("这是查到的 "+attdDate);

            String nowDateStr = dateFormat.format(nowDate).toString();
            String attdDateStr = attdDate.toString();
            /*
            //调试用代码
            AttdInfoVo attdInfoVo = studentMapper.getAttdInfoById(attdId);
            return attdInfoVo;
            */
            //判断日期是否相等
            System.out.println("javaTime--"+nowDateStr);
            System.out.println("sqlTime"+attdDateStr);
            System.out.println(nowDateStr.equals(attdDateStr));

            if(nowDateStr.equals(attdDateStr)){
                //相等则去查询相关信息
                AttdInfoVo attdInfoVo = studentMapper.getAttdInfoById(attdId);
                attdInfoVo.setAttdId(String.valueOf(attdId));
                return attdInfoVo;
            }
            else{
                throw new RuntimeException("您今日考勤仍未查询到");
            }
        }
    }

    @Override
    public List<AttdLogVo> getAttdLog(Integer stuId) {
        int count = studentMapper.getAttdLogCount(stuId);
        if(count > 0){
            List<AttdLogVo> attdLogVos = studentMapper.getAttdLogsById(stuId);
            return attdLogVos;
        }
        else
            throw new RuntimeException("未查询到考勤记录");
    }

    @Override
    public Student getMyInfo(Integer stuId) {
        Student student = studentMapper.getMyInfo(stuId);
        return student;
    }
    @Override
    public void changePwd(WebLoginVo webLoginVo){
        try{
            studentMapper.updatePwdById(webLoginVo.getUserId(),webLoginVo.getPassword());
        }catch (Exception ignored){

        }
    }

    @Override
    public String analyFaceEncode(String flagName) {
        String url = appvalue.getPythonUrl()+"/faceapi";
        String faceEncoding = NetworkUtil.
                sendPostSuccess(url,
                        flagName);
        return faceEncoding;
    }

    @Override
    public StudentVo compareFace(String newFaceCode, int teahId, int subId) {
        //python接口
        String url = appvalue.getPythonUrl()+"/faceCompare";

        //首先获取所有的faceCode ==>优化，改为只寻找班级内学生编码
        int classId = studentMapper.findClass(teahId,subId);
        List<StudentFaceVo> studentFaceVoList = studentMapper.getAllFaceCodes(classId);
        for (StudentFaceVo studentFace : studentFaceVoList) {
            String faceCode = studentFace.getStuFace();
            //将所有已经录入的code逐一与faceEncoding进行对比
            //这里开始区分方法1和方法2
            System.out.println("判断是否录入人脸");
            if (faceCode != null) {
                //方法1:调用python接口 ->我暂时还没搞明白怎么调用 ->先逝一逝
                /*中途遇到了不少困难
                 * 其一：python接口的编写，这个遇到了一点小问题，不是很困难
                 * 其二：传到服务器端的数据类型是str，而face_recognition的对比函数要求是二维的numpy矩阵
                 *      这个花了不少时间，先是自己尝试改变数据类型，但是发现转换后的数据精度不够（最后才发现
                 *      是控制台显示的精度低，实际上精度不变=>要搞死我啦花了3个小时）最终在网上找到了一篇类
                 *      似的博客，参照他的方法优化了格式变更，博客地址：
                 *      https://blog.csdn.net/weixin_43272781/article/details/103793031
                 * */
                String matcheFlag = NetworkUtil.compareFace(url, faceCode, newFaceCode);
                System.out.println("matchFlag" + matcheFlag);
                if (matcheFlag.equals("\"YES\"")) {
                    StudentVo student = new StudentVo();
                    student.setStuId(studentFace.getStuId());
                    student.setStuName(studentFace.getStuName());
                    return student;
                }
            }
        }
                //方法2:调用自己写的对比算法 ->

        return null;
    }

    @Override
    @Transactional
    public Boolean upDateAttdLog(SignVo signVo) {
        Map params = new HashMap<>();
        String str = signVo.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date dateTime = null;
        String d = null;
        System.out.println("sign.getTime"+str);


        try {
            dateTime = format.parse(str);
            System.out.println("格式化的JavaTime ： "+dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Timestamp time = new java.sql.Timestamp(dateTime.getTime());
        //System.out.println("Java转生的sql"+time);
        try {

            params.put("attdStuId", signVo.getAttdStuId());
            params.put("attdStuName", signVo.getAttdStuName());
            params.put("attdId", Long.valueOf(signVo.getAttdId()));
            params.put("time", time);
            params.put("flag", signVo.getFlag());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(signVo.getSignFlag().equals("signIn")){
        //    是签到
            try{
                studentMapper.updateAttdLogIn(params);
                return true;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        else{
            try{
                studentMapper.updateAttdLogOut(params);
                return true;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    private Boolean myFaceCompare(String newFaceCode,String faceCode){
        return false;
    }
}
