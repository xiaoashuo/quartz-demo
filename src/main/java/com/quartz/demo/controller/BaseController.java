package com.quartz.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.quartz.demo.common.ServletUtils;
import com.quartz.demo.common.StringUtils;
import com.quartz.demo.common.constants.Constants;
import com.quartz.demo.common.dto.TableList;

import com.quartz.demo.util.DateUtils;
import com.quartz.demo.util.sql.SqlUtil;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

public class BaseController {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    protected void startPage(){
        Integer pageSize=ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
        Integer pageNum= ServletUtils.getParameterToInt(Constants.PAGE_NUM);
        String orderByName=ServletUtils.getParameter(Constants.ORDER_BY_COLUMN);
        String isAsc=ServletUtils.getParameter(Constants.IS_ASC);

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            //检查是否为sql注入
            String orderBy = SqlUtil.escapeOrderBySql(getOrderBy(orderByName,isAsc));
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 拼接排序字段 order +判断 若为空排序列 直接返回""
     * @param orderByColumn
     * @param isAsc
     * @return
     */
    private String getOrderBy(String orderByColumn,  String isAsc)
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    protected TableList getDataTables(List<?> list){
        TableList tableList = new TableList();
        tableList.setCode(0);
        tableList.setRows(list);
        tableList.setTotal(new PageInfo<>(list).getTotal());
        return tableList;
    }

}
