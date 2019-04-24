package com.quartz.demo.controller;

import com.github.pagehelper.PageInfo;
import com.quartz.demo.common.dto.BaseResult;
import com.quartz.demo.common.dto.TableList;
import com.quartz.demo.entity.Job;
import com.quartz.demo.exception.job.TaskException;
import com.quartz.demo.service.IJobService;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/monitor/job")
public class JobController extends BaseController
{
    private String prefix = "job";

    @Autowired
    private IJobService jobService;


    @GetMapping()
    public String job()
    {
        return  prefix+"/job_list";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableList list(Job job)
    {
        startPage();
        List<Job> list = jobService.selectJobList(job);
        //PageInfo<Job> pageInfo=new PageInfo<>(list);
        TableList dataTables = getDataTables(list);
        return dataTables;
    }





    @PostMapping("/remove")
    @ResponseBody
    public BaseResult remove(String ids) throws SchedulerException
    {
        jobService.deleteJobByIds(ids);
        return BaseResult.success();
    }


    @GetMapping("/detail/{jobId}")
    public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("name", "job");
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/job_detail";
    }

    /**
     * 任务调度状态修改
     * 参数1：jobId
     * 参数2：status
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public BaseResult changeStatus(Job job) throws SchedulerException
    {
        return BaseResult.toAjax(jobService.changeStatus(job)) ;
    }

    /**
     * 任务调度立即执行一次
     * 入参jobId
     */
    @PostMapping("/run")
    @ResponseBody
    public BaseResult run(Job job) throws SchedulerException
    {
        jobService.run(job);
        return BaseResult.success("执行成功");
    }

    /**
     * 新增调度
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/job_add";
    }

    /**
     * 新增保存调度
     */

    @PostMapping("/add")
    @ResponseBody
    public BaseResult addSave(Job job) throws SchedulerException, TaskException
    {
        int i = jobService.insertJobCron(job);
        if (i>0){
            return BaseResult.success("保存成功");
        }
        return BaseResult.fail("保存失败");
    }

    /**
     * 修改调度
     */
    @GetMapping("/edit/{jobId}")
    public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/edit";
    }

    /**
     * 修改保存调度
     */


    @PostMapping("/edit")
    @ResponseBody
    public BaseResult editSave(Job job) throws SchedulerException, TaskException
    {
        return BaseResult.toAjax(jobService.updateJobCron(job));
    }

    /**
     * 校验cron表达式是否有效
     */
    @PostMapping("/checkCronExpressionIsValid")
    @ResponseBody
    public boolean checkCronExpressionIsValid(Job job)
    {
        return jobService.checkCronExpressionIsValid(job.getCronExpression());
    }
}
