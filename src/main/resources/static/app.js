var App=function () {

    var post=function (url,data,callback) {
        send(url,"post","json",data,callback)
    }
    var get=function (url,data,callback) {
        send(url,"get","json",data,callback);
    }
    var send=function (url, type, dataType, data,callback) {
        $.ajax({
            url: url,
            type: type,
            data: data,
            dataType: dataType,
            success:callback,
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("请求失败");
            }
        });
    }

    /******************BootStrap Table Init Begin***********************/
    var Table=null;
    var bootstrap_table_default={
        url: '',         //请求后台的URL（*）
        index:"",   //表格索引
        method: 'post',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortName: "",             //排序列名
        sortOrder: "asc",                   //排序方式
       // queryParams: queryParams,//传递参数（*）
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 3,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        paginationLoop: false,
        paginationFirstText: "首页",
        paginationPreText: "上一页",
        paginationNextText: "下一页",
        paginationLastText: "末页",
        search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        contentType: "application/x-www-form-urlencoded",
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "no",                     //每一行的唯一标识，一般为主键列
        showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表

        //按钮样式
        // buttonsClass: 'btn',
        //分页器class
        //iconSize: 'pager',
        //得到查询的参数传递参数（*）
        queryParams : queryParams,
        rowStyle: function (row, index) {
            var classesArr = ['success', 'info'];
            var strclass = "";
            if (index % 2 === 0) {//偶数行
                strclass = classesArr[0];
            } else {//奇数行
                strclass = classesArr[1];
            }
            return { classes: strclass };
        },//隔行变色
        onLoadSuccess: function (res) {//可不写
            //加载成功时
            // console.log(res);
        }, onLoadError: function (statusCode) {
            return "加载失败了"
        }, formatLoadingMessage: function () {
            //正在加载
            return "拼命加载中...";
        }, formatNoMatches: function () {
            //没有匹配的结果
            return '无符合条件的记录';
        }
    }
     function  queryParams (params) {
         var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
             limit: params.limit,   //页面大小
             offset:params.offset / params.limit + 1, //页码
             isAsc: params.order,
             orderByColumn: params.sort,

         };

        var jsonObj= form2JsonObj(null,0);
         $.extend(temp,jsonObj);
         return temp;
    };

    /**
     * 初始化表格
     * @param options
     * @constructor
     */
    var InitTable=function (options) {
      var options=  $.extend(bootstrap_table_default,options);
      Table=$("#"+options.id);


        $(window).resize(function () {
            //防止表头与表格不对齐
            Table.bootstrapTable('resetView');
        });
        //初始化Table
        //先销毁表格
        Table.bootstrapTable('destroy');
        Table.bootstrapTable(options);



    }
    /**
     * 刷新表格数据
     * 并返回首页
     */
    function refresh() {
        Table.bootstrapTable('refresh',{pageNumber:1});
        //$table.bootstrapTable('refresh'.{url:""});//刷新时调用接口防止表格无限销毁重铸时出现英文
    }

    /**
     * 搜索
     * @param formId
     * @param index
     */
    var search = function (formId,index) {
        var params = Table.bootstrapTable('getOptions');
        params.pageNumber=1;
    /*    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset:params.offset / params.limit + 1, //页码
            isAsc: params.order,
            orderByColumn: params.sort,

        };
        var jsonObj= form2JsonObj(null,0);
        var extData={query:jsonObj};
        $.extend(params,temp,extData);

        console.log(params);*/

        //Table.bootstrapTable('refresh', params)
       // Table.bootstrapTable("load",params);
        params.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset:params.offset / params.limit + 1, //页码
                isAsc: params.order,
                orderByColumn: params.sort,

            };
            var jsonObj= form2JsonObj(null,0);
            $.extend(temp,jsonObj);
            return temp;
        }
        console.log(params)

     Table.bootstrapTable('refreshOptions', params);
     //Table.bootstrapTable('refresh', params);




    };

    /******************BootStrap Table Init End***********************/
    /**
     * 验证是否为空
     * @param str
     * @returns {boolean}
     */
    var isEmpty=function (str) {
        if (str=== "undefined"||str===""||str===null){
            return true;
        }
        return false;
    };
    /**
     * 将指定表单序列化成json对象 而不是serialize那种字符串
     * @param formId 表单id  若未传
     * @param index 表单索引，不传默认为0 即第一个
     */
    var form2JsonObj=function (formId,index) {
        var currentId=isEmpty(formId)?$($("form")[isEmpty(index)?0:index]).attr("id"):formId;
        var serializeObj = {};
        var array = $("#"+currentId).serializeArray();
        $.each(array,function () {
            //判断是否存在
            if (serializeObj[this.name]) {
                //判断是否为数组 则追加值
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                }
                //若不是 则创建 数组 因为第一次是[arg1,arg2] arg1=第一次存取的k 打印下就知道了
                else {
                    serializeObj[this.name] = [
                        serializeObj[this.name], this.value ];
                }
            }
            //若不存在 则存入 k:v
            else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    }
    /**
     * 重置表单
     * @param index
     */
    var resetForm=function (index) {
        $("form")[index].reset();
    }
    return{
        sendPost:function (url,data,callback) {
            return post(url,data,callback);
        },
        form2JsonObj:function (formId,index) {
           return  form2JsonObj(formId,index);
        },
        resetForm:function (index) {
            return resetForm(index);
        },
        InitTable:function (options) {

            return InitTable(options);
        },
        refresh:function(){
            refresh();
        },
        search:function (formId,index) {
            return search(formId,index);
        }

    }

}()
