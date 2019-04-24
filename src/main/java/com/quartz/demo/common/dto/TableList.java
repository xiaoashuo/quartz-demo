package com.quartz.demo.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class TableList {
    /** 总记录数 */
    private long total;
    /** 列表数据 */
    private List<?> rows;
    /** 消息状态码 */
    private int code;
}
