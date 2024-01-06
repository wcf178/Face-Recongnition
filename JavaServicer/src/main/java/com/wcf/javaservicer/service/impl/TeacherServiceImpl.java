package com.wcf.javaservicer.service.impl;

import com.wcf.javaservicer.mapper.SubjectMapper;
import com.wcf.javaservicer.mapper.TeacherMapper;
import com.wcf.javaservicer.pojo.Subject;
import com.wcf.javaservicer.pojo.Teacher;
import com.wcf.javaservicer.service.TeacherService;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.WebLoginVo;
import com.wcf.javaservicer.vo.WebSubVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Override
    public Result findAll() {
        log.info("获取用户信息成功");
        return Result.success("获取用户信息成功",teacherMapper.findAll());
    }

    @Override
    public Result login(WebLoginVo webLoginVo) {
        log.info("检验用户是否存在");
        String pwd = teacherMapper.getPwdById(webLoginVo.getUserId());

        if(webLoginVo.getPassword().equals(pwd)){
            String permission = teacherMapper.getPermission(webLoginVo.getUserId());
            return new Result(true,"登陆成功",permission);
        }
        else{
            return new Result(false,"登陆失败，密码错误");
        }
    }

    @Override
    public Result getInfoById(int teahId) {
        Teacher teacher = teacherMapper.findInfoById(teahId);

        return new Result(true,"获取教师信息成功",teacher);
    }

    @Override
    public List<WebSubVo> getSubListById(int teahId) {

        List<Integer> subIds = teacherMapper.findSubsById(teahId);
        List<WebSubVo> webSubVoList = new ArrayList<WebSubVo>();
        for(Integer subId:subIds){
            WebSubVo webSubVo = subjectMapper.findSubById(subId);
            webSubVoList.add(webSubVo);
        }
        return webSubVoList;
    }
}
