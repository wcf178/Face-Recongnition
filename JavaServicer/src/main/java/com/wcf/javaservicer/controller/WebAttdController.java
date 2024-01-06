package com.wcf.javaservicer.controller;


import com.wcf.javaservicer.pojo.Attd;
import com.wcf.javaservicer.service.AttdService;
import com.wcf.javaservicer.utils.PageResult;
import com.wcf.javaservicer.utils.QueryPageBean;
import com.wcf.javaservicer.utils.Result;
import com.wcf.javaservicer.vo.AttdQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attd")
public class WebAttdController {

    @Autowired
    private AttdService attdService;
    @RequestMapping("/check")
    public PageResult checkAttdLog(@RequestBody QueryPageBean queryPageBean){
        System.out.println(queryPageBean.getQueryString());
        PageResult pageResult = attdService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString(),
                queryPageBean.getQueryState());
//        System.out.println(pageResult.getRows());
        return pageResult;
    }
    @RequestMapping("/getAttdId")
    public Result getAttdIdByOpt(AttdQueryVo attdQueryVo){
        try{
            System.out.println("controller层教你做事");
            System.out.println(attdQueryVo.getAttdDate());
            String attdId = String.valueOf(attdService.getAttdIdByOpt(attdQueryVo));
            if(attdId == null){
                return  new Result(false,"获取考勤Id失败");
            }
            return new Result(true,"获取考勤Id成功",attdId);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"获取考勤Id失败");
        }

    }
    @RequestMapping("/publish")
    public Result publishAttd(Attd attd){
        try{
            System.out.println("controller层教你做事");
            System.out.println(attd.getAttdDate());
            System.out.println(attd.getTeahId());
            System.out.println(attd.getSubId());
            attdService.addAttd(attd.getTeahId(),attd.getSubId(),attd.getAttdDate(),attd.getStartTime(),attd.getEndTime(),attd.getLatitude(),attd.getLongitude(),attd.getDistance());
            return new Result(true,"发布考勤成功");
        }catch (Exception e) {

            return new Result(false,e.getMessage());
        }

    }


}
