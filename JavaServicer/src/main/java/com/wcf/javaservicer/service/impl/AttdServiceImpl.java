package com.wcf.javaservicer.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wcf.javaservicer.mapper.AttdLogMapper;
import com.wcf.javaservicer.pojo.Attdlog;
import com.wcf.javaservicer.service.AttdService;
import com.wcf.javaservicer.utils.PageResult;
import com.wcf.javaservicer.utils.SnowflakeIdWorker;
import com.wcf.javaservicer.vo.AttdQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.beans.Transient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AttdServiceImpl implements AttdService {
    @Autowired
    private AttdLogMapper attdLogMapper;
    @Override
    @Transient
    public long getAttdIdByOpt(AttdQueryVo attdQueryVo) {
        log.info("Service层查询attdId");
        log.info(attdQueryVo.getAttdDate());
        //将String转换为Date
        String dateString = attdQueryVo.getAttdDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
            Date date1 = new java.sql.Date(date.getTime());
            System.out.println(date1);
            Map params = new HashMap<>();
            params.put("teahId",attdQueryVo.getTeahId());
            params.put("subId",attdQueryVo.getSubId());
            params.put("attdDate",date1);
            long attdId = attdLogMapper.getAttdIdByOpt(params);
            return attdId;
            //date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    @Transient
    public PageResult pageQuery(Integer currentPage, Integer pageSize, long queryString, int queryState) {
        PageHelper.startPage(currentPage,pageSize);
        Map params = new HashMap<>();
        params.put("attdId",queryString);




        if(queryState != 2){
            if(queryState == 3){
                queryState = 0;
            }
            params.put("attdFlag",queryState);
            Page<Attdlog> page = attdLogMapper.selectByAttdId(params);
            PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
            return pageResult;
        }
        else{
            Page<Attdlog> page = attdLogMapper.selectEarlyByAttdId(queryString);
            PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
            return pageResult;
        }
    }

    @Override
    @Transactional
    public void addAttd(int teahId, int subId, String attdDate, String startTime, String endTime, BigDecimal latitude, BigDecimal longitude, Integer distance) {

        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);
        long attdId = snowflakeIdWorker.nextId();
        log.info(String.valueOf(attdId));
        String dateString = attdDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try{
            date = sdf.parse(dateString);
            Date date1 = new java.sql.Date(date.getTime());
            System.out.println(date1);
            Map checkCount = new HashMap<>();
            checkCount.put("teahId",teahId);
            checkCount.put("subId",subId);

            checkCount.put("attdDate",date1);
            int countOfTodayAttd=attdLogMapper.getTodayAttdCount(checkCount);
            if(countOfTodayAttd > 0){
                throw new RuntimeException("您今日已发布一次考勤，不能再发了（功能受限）");
            }
            Map params = new HashMap<>();
            params.put("teahId",teahId);
            params.put("subId",subId);
            params.put("attdId",attdId);
            params.put("attdDate",date1);
            params.put("startTime",startTime);
            params.put("endTime",endTime);
            params.put("latitude",latitude);
            params.put("longitude",longitude);
            params.put("distance",distance);
            int classId = attdLogMapper.getClassId(teahId,subId);
            String stuName = null;
            System.out.println("这他妈的使班级IID为什么会空"+ classId);
            attdLogMapper.addAttd(params);
            List<Integer> stuIds = attdLogMapper.findStudentIdsByClassId(classId);
            for(Integer stuId:stuIds){
                //为学生更新attdId,并且为考勤表更新
                stuName = attdLogMapper.getStuNameById(stuId);
                attdLogMapper.addAttdtoStudent(stuId,attdId);
                attdLogMapper.addNewLog(stuId,attdId,stuName);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
