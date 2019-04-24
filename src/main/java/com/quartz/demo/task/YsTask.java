package com.quartz.demo.task;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ysTask")
public class YsTask
{
    public void ysParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ysNoParams()
    {
        System.out.println("执行无参方法");
    }
    public String ysReParams()
    {
        System.out.println("执行有返回值方法");
        return "1";
    }
    public static void main(String[] args) {
        try {
            System.out.println("---"+ YsTask.class.getName());
            Object ryTask = Class.forName(YsTask.class.getName()).newInstance();
            Method method = ryTask.getClass().getDeclaredMethod("ysReParams");
            Object invoke = method.invoke(ryTask);
            System.out.println(invoke+"我是返回回来的"+invoke);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
