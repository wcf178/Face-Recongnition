package com.wcf.javaservicer.controller;


import com.wcf.javaservicer.config.ApplicationValues;
import com.wcf.javaservicer.pojo.Student;
import com.wcf.javaservicer.service.StudentService;
import com.wcf.javaservicer.utils.FileManage;
import com.wcf.javaservicer.utils.NetworkUtil;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/AppLetsController")
public class AppLetsController {

    @Autowired
    private ApplicationValues appvalue;

    @Value("${uploadface}")
    private String UPLOAD_PATH;

    @Value("${upload}")
    private String UPLOAD_RORTRAIT;

    private static final Logger logger = LoggerFactory.getLogger(AppLetsController.class);

    @Autowired
    private StudentService studentService;
    @RequestMapping("/appUpdate")
    public Result appUpdate(MultipartFile file,Integer stuId){

        String flagName = FileManage.savePicture(file, UPLOAD_PATH);
        if (!flagName.isEmpty()){
            String s = studentService.analyFaceEncode(flagName);
            if(s.equals("\"NO_FACE\"")){
                return new Result(false,"未检测到人脸");
            }
            //图片存储成功，通知python服务器解析图片
            String faceEncoding = s.substring(1,s.length()-1);
            System.out.println(s.length());
            System.out.println(faceEncoding.length());
            logger.info(faceEncoding);
            //设置人脸编码
            //student.setStuFace(faceEncoding);
            //String face = studentService.findFaceById(stuId);
            //判断原来是否存在人脸数据 -->不检测了，直接更新

            studentService.updateFaceById(faceEncoding, stuId);
            return Result.success("添加成功");


        }else{
            return Result.fail("添加失败");

        }
    }
    @RequestMapping("/faceCompare")
    public Result faceCompare(AttdQueryVo attdQueryVo,MultipartFile file){
        String flagName = FileManage.savePicture(file, UPLOAD_PATH);

        if (!flagName.isEmpty()) {
            //这一步是去掉[]
            String s = studentService.analyFaceEncode(flagName);
            if(s.equals("\"NO_FACE\"")){
                return new Result(false,"未检测到人脸");
            }
            String faceEncoding = s.substring(1,s.length()-1);

            //图片存储成功，通知python服务器解析图片

            logger.info(faceEncoding);
            //与数据库中的人脸数据进行对比
            /*
            * 方法1：将分析得到的人脸编码和数据库的每个人脸编码传输到python服务器进行对比
            *
            * 方法2：直接在Java服务器中写一个人脸对比算法
            *
            * */

            //不管怎样，先查询到数据库的人脸编码吧==>算了交给service层做吧
            if(!faceEncoding.isEmpty()){

                StudentVo student = studentService.compareFace(faceEncoding,attdQueryVo.getTeahId(),attdQueryVo.getSubId());
                if(student == null){
                    return new Result(false,"未在人脸库中");
                }
                else{
                    return new Result(true,"人脸对比成功",student);
                }

            }
            else{
                return new Result(false,"未识别到人脸~");
            }


        }
        else{
            return new Result(false,"未找到图片");
        }

    }
    @PostMapping(value="/sign")
    public Result sign(@RequestBody SignVo signVo){
        try{
            Boolean res = studentService.upDateAttdLog(signVo);
            return new Result(res,"考勤成功");
        }catch (Exception e){
            return new Result(false,e.getMessage());
        }
    }
    @PostMapping(value = "/login")
    public Result loginForStudent(@RequestBody WebLoginVo webLoginVo){
        try{
            int loginFlag = studentService.login(webLoginVo);
            if(loginFlag == 1){
                return new Result(true,"登陆成功! ",loginFlag);
            } else if (loginFlag == 2) {
                return new Result(true,"登陆成功!\n需人脸录入！",loginFlag);
            } else if(loginFlag == 0){
                return new Result(false,"登陆失败，密码或账号错误! ");

            }
            else{
                return new Result(false,"账户不存在！");
            }

        }catch (Exception e){
            return new Result(false,e.getMessage());
        }
    }
    @RequestMapping("/getAttd")
    public Result getAttendance(Integer stuId){
        try{
            AttdInfoVo attdInfoVo = studentService.getAttd(stuId);

            return new Result(true,"您收到一条考勤信息！",attdInfoVo);
        }
        catch(Exception e){
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping("/getAttdLog")
    public Result getAttdLog(Integer stuId){
        try{
            List<AttdLogVo> attdLogList = studentService.getAttdLog(stuId);
            return new Result(true,"获取考勤列表信息成功",attdLogList);
        }catch(Exception e){
            return  new Result(false,e.getMessage());
        }
    }
    @RequestMapping("/getMyInfo")
    public Result getMyInfo(Integer stuId){
        try{
            return new Result(true,"获取信息成功",studentService.getMyInfo(stuId));
        }catch(Exception e){
            return new Result(false,e.getMessage());
        }

    }
    @RequestMapping("/changePassword")
    public Result changePwd(WebLoginVo webLoginVo){
        try{
            studentService.changePwd(webLoginVo);
            return new Result(true,"修改密码成功");
        }catch (Exception e){
            return  new Result(false,e.getMessage());
        }
    }
}



/*
* 小程序控制器
* 负责和小程序的通讯

@RestController
@RequestMapping("/AppletsController")
public class appLetsController {


    @RequestMapping("/appUpdate")
    public RespBean appUpdate(MultipartFile file, Person person){
        String accountId = person.getId();
        Face face = new Face();
        String flagName = FileManage.savePicture(file, UPLOAD_PATH);
        if (!flagName.isEmpty()){
            //保存图片的路径
            face.setUrl(flagName);
            //图片存储成功，通知python服务器解析图片
            String faceEncoding = NetworkUtil.
                    sendPostSuccess(appvalue.getPythonUrl(),
                            flagName,person.getAccount());
            logger.info(faceEncoding);
            //设置人脸编码
            face.setFacecoding(faceEncoding);
            //保存到人脸数据库
            PersonFace personFace = faceCodingServiceImpl.selectUserFaceCoding(accountId);
            //判断原来是否存在人脸数据
            if (personFace !=null){
                faceCodingServiceImpl.updateFaceCoding(face, personFace);
            }else {
                faceCodingServiceImpl.insertFaceCoding(face,accountId);
            }
            return RespBean.success("添加成功");
        }else{
            return RespBean.error("添加失败");
        }
    }
}
*/