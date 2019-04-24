package com.quartz.demo.common.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResult implements Serializable {
    private static final long serialVersionUID = -3234526486867173207L;
    private static final int SUCCESS_CODE=200;
    private static final String SUCCESS_MESSAGE="success";
    private static final int FAIL_CODE=200;
    private static final String FAIL_MESSAGE="fail";
    private int status;
    private String message;

    public static BaseResult success(String message){
       return createBaseResult(SUCCESS_CODE,message);
    }
    public static BaseResult success(){
        return createBaseResult(SUCCESS_CODE,SUCCESS_MESSAGE);
    }
    public static BaseResult toAjax(int result){
        return result>0?BaseResult.success():BaseResult.fail();
    }
    public static BaseResult fail(String message){
       return createBaseResult(FAIL_CODE,message);
    }
    public static BaseResult fail(){
       return createBaseResult(FAIL_CODE,FAIL_MESSAGE);
    }
    private  static BaseResult createBaseResult(int status,String message){
        BaseResult baseResult=new BaseResult();
        baseResult.setStatus(status);
        baseResult.setMessage(message);
        return baseResult;
    };
}
