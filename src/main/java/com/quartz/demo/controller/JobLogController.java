package com.quartz.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quartz.demo.common.dto.BaseResult;
import com.quartz.demo.entity.JobLog;
import com.quartz.demo.service.IJobLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度日志操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class JobLogController
{
    private String prefix = "monitor/job";

    @Autowired
    private IJobLogService jobLogService;


    @GetMapping()
    public String jobLog()
    {
        return prefix + "/jobLog";
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfo list(JobLog jobLog)
    {
        PageHelper.startPage(0,10);
        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
        PageInfo<JobLog> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }





    @PostMapping("/remove")
    @ResponseBody
    public BaseResult remove(String ids)
    {
        return BaseResult.toAjax(jobLogService.deleteJobLogByIds(ids));
    }
    

    @GetMapping("/detail/{jobLogId}")
    public String detail(@PathVariable("jobLogId") Long jobLogId, ModelMap mmap)
    {
        mmap.put("name", "jobLog");
        mmap.put("jobLog", jobLogService.selectJobLogById(jobLogId));
        return prefix + "/detail";
    }
    

    @PostMapping("/clean")
    @ResponseBody
    public BaseResult clean()
    {
        jobLogService.cleanJobLog();
        return BaseResult.success();
    }
}
