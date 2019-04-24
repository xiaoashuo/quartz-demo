package com.quartz.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    public void invokeTest(){
        System.out.println("我是无参反射引用");
    }

    @Test
    public void contextLoads() {
        String substring = DigestUtils.sha256Hex("123456").substring(0, 15);
        System.out.println(substring);
        //  int count = testMapper.count();
      //  System.out.println(count);
        //    resumeJob();
    //   String m="1,2,4";
       String [] mm={"1","2","3"};
        System.out.println(Arrays.toString(mm));
   /*     String[] split = m.split(",");
        for (String s : split) {
            System.out.println(s);

        }*/

    }

    private void resumeJob() throws SchedulerException {
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = new JobKey("job_1", "jGroup_1");
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        if (triggers.size()>0){
            for (Trigger trigger : triggers) {
                if ((trigger instanceof CronTrigger)||(trigger instanceof SimpleTrigger))
                {
                    scheduler.resumeJob(jobKey);
                }
            }
            scheduler.start();
        }
    }

    /*private void startSchedule() throws SchedulerException, InterruptedException {
        //JobDetail实列 指定Quartz
        JobDetail jobDetail= JobBuilder.newJob(NewJob.class)
                .withDescription("MyJob Detail Jdbc")
                .withIdentity("job_1","jGroup_1")
                .build();
        //触发器
        //触发器类型 简单触发器
//        SimpleScheduleBuilder builder = SimpleScheduleBuilder
//                // 设置执行次数
//                .repeatSecondlyForTotalCount(5);
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        //创建Trigger
        Trigger trigger=TriggerBuilder.newTrigger()
                .withDescription("My Trigger Creat")
                .withIdentity("trigger_1","tGroup_1").startNow()
                .withSchedule(builder).build();
        //创建执行者 scheduler
        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);
       // Thread.sleep(6000);
       // scheduler.shutdown();

    }
*/

}
