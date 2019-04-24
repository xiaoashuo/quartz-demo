package com.quartz.demo.util.quartz;

import com.quartz.demo.common.StringUtils;
import com.quartz.demo.common.spring.SpringUtils;
import com.quartz.demo.entity.Job;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 任务执行工具
 *
 *
 */
public class JobInvokeUtil
{
    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static void invokeMethod(Job job) throws Exception
    {
        Object bean = SpringUtils.getBean(job.getJobName());
        String methodName = job.getMethodName();
        String methodParams = job.getMethodParams();

        invokeSpringBean(bean, methodName, methodParams);
    }

    /**
     * 调用任务方法
     *
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    private static void invokeSpringBean(Object bean, String methodName, String methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException
    {
        //有参方法
        if (StringUtils.isNotEmpty(methodParams))
        {
            //通过反射获取指定名称的方法 （仅仅是当前类 的 方法 父类不包括在内）
            Method method = bean.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(bean, methodParams);
        }
        //无参方法
        else
        {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }
}
