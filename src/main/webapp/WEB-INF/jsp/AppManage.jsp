<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/9
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html class="iframe-h">

<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>网站后台管理模版</title>
    <link rel="stylesheet" href="./plugins/layui/css/layui.css" media="all">

    <style>
        .x-item {
            margin-bottom: 0px;
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="wrap-container clearfix">
    <div class="column-content-detail">

<%--        <form class="layui-form " action="" style="width:800px">--%>
            <div class="layui-form-pane" style="margin-top: 15px;">
                <div class="layui-form-item  x-item">
                    <div class="layui-input-inline">
                        <input type="text" name="appname" required lay-verify="required" placeholder="请输入应用名称" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-input-inline" style="width:80px">
                        <button class="layui-btn" lay-filter="sreach" id="search"><i class="layui-icon"></i></button>
                    </div>
                </div>
            </div>
<%--        </form>--%>
        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-normal" type="button" lay-event="add">新增</button>
                <button class="layui-btn layui-btn-danger " type="button" lay-event="batchDel">批量删除</button>
            </div>
        </script>
        <div class="layui-form" id="table-list">
            <table id="demo" lay-filter="demo"  ></table>
            <script type="text/html" id="barDemo">
                <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            </script>
        </div>
    </div>
</div>
<script src="./js/jquery.min.js"></script>
<script src="./js/jquery.form.js"></script>
<script src="./plugins/layui/layui.js"></script>
<script src="./plugins/admin/js/common.js" ></script>
<script>
    var table = null;
    layui.use('table', function () {
        table = layui.table;
        //监听工具条
        table.on('tool(demo)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    del(data.id);
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                update(data.appname,data.id);
            }
        });
        //头工具栏事件
        table.on('toolbar(demo)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                //新增
                case 'add':
                    //清空form表单
                    $("#bj").clearForm();
                    var data = checkStatus.data;
                    layer.open({
                        type: 1
                        , title: "修改路线信息"
                        , content: $("#bj")
                        , area: ['500px', '320px']
                        , btn: ['提交', '取消'] //只是为了演示
                        , yes: function () {
                            if($("input[name=AppName]").val()==undefined||$("input[name=AppName]").val()==null||$("input[name=AppName]").val()=="")
                            {
                                layer.alert("请输入应用名",{
                                    icon:2,
                                    title:'错误'
                                });
                                return false;
                            }
                            $.ajax({
                                type: "post"
                                , url: "/AppManage/add"
                                , dataType: 'json'
                                ,contentType : 'application/json;charset=utf-8'
                                ,data:JSON.stringify({
                                    appname:$("input[name=AppName]").val()
                                    ,id:$("input[name=Id]").val()
                                })
                                , success: function (data) {
                                    layer.msg(data.msg, {time: 1000});
                                    if (data.code == 3) {
                                        setTimeout(function () {
                                            esc();
                                            table.reload('idsw');
                                        },500)
                                    }
                                }
                            });
                        }
                        , btn2: function () {
                            esc();
                        }, cancel: function (index, layero) {
                            esc()
                            return false;
                        }
                    });
                    break;
                case 'search':
                    table.reload('idsw', {
                        where: $("#searchForm").serializeObject()//用jquery.jdirk.js实现的，将form表单中的input值转成json格式
                        ,page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    break;
                case 'batchDel':
                    var data = checkStatus.data;
                    if(data.length==0){
                        layer.alert("请至少选中一行再进行操作",{
                            icon:2,
                            title:'错误'
                        });
                        break;
                    }
                    //准备一个id数组
                    var ids=[];
                    $.each(data,function (index, obj) {
                        ids.push(obj.id)
                    })
                    layer.confirm('确定删除这些数据?', function(index) {
                        $.post("/AppManage/batchDel", {"ids": ids.toString()}, function (result) {
                            if (result.code=="3") {
                                //刷新页面
                                table.reload('idsw',{page:{curr:1}});
                                layer.close(index);
                            } else {
                                layer.alert(result.msg, {
                                    icon: 2,
                                    title: '错误'
                                });
                            }
                        })
                    })
                    break;
            };
        });

        xrsj("json");
    });
</script>

<script>
    function  update(appname,id) {
        $("input[name=AppName]").val(appname);
        $("input[name=Id]").val(id);
        layer.open({
            type: 1
            , title: "修改路线信息"
            , content: $("#bj")
            , area: ['500px', '320px']
            , btn: ['修改', '取消'] //只是为了演示
            , yes: function () {
                if($("input[name=AppName]").val()==undefined||$("input[name=AppName]").val()==null||$("input[name=AppName]").val()=="")
                {
                    layer.alert("请输入应用名",{
                        icon:2,
                        title:'错误'
                    });
                    return false;
                }
                $.ajax({
                    type: "post"
                    , url: "/AppManage/update"
                    , dataType: 'json'
                    ,contentType : 'application/json;charset=utf-8'
                    ,data:JSON.stringify({
                        appname:$("input[name=AppName]").val()
                        ,id:$("input[name=Id]").val()
                    })
                    , success: function (data) {
                        layer.msg(data.msg, {time: 1000});
                        if (data.code == 3) {
                            setTimeout(function () {
                                esc();
                                table.reload('idsw');
                            },500)
                        }
                    }
                });
            }
            , btn2: function () {
                esc();
            }, cancel: function (index, layero) {
                esc()
                return false;
            }
        });
    }

    var $ = layui.$, active = {
        reload: function(){
            var appname = $("input[name=appname]").val();
            table.reload('idsw', {
                where: {
                    appname:  $("input[name=appname]").val()
                }
            });
        }
    };
    $('#search').on('click', function(){
        active["reload"] ? active["reload"].call(this) : '';
    });
    function xrsj(url) {
        //进行渲染
        table.render({
            elem: '#demo'
            ,toolbar: '#toolbarDemo'
            , page: true
            , url: '/AppManage/'+url //数据接口
            //,method:'POST'
            , cellMinWidth: 182
            , cols: [[ //表头
                {type:'checkbox'},
                {type:'numbers',title: '序号'}
                , {field: 'appname', title: '应用名', align: 'center'}
                , {field: 'addtime', title: '添加时间', align: 'center'}
                , {field: 'updatetime', title: '修改时间', align: 'center'}
                , {field: 'right', title: '操作', width: '178', align: 'center', toolbar: '#barDemo'}
            ]]
            , id: "idsw"
        });
    }

    //删除公司
    function del(routeNo) {
        $.ajax({
            type: "post"
            , url: "/AppManage/del/" + routeNo
            , dataType: 'json'
            , success: function (data) {
                layer.msg(data.msg, {time: 1000});
                if (data.code == 3) {
                    table.reload('idsw');
                }
            }
        });
    }
    //关闭回调事件
    function esc() {
        layer.closeAll();
        $("input[name=AppName]").val("");
        $("input[name=Id]").val("");
        $("#bj").attr("hidden", "hidden").css("display", "none");
    }

</script>
</body>
<form id="bj" class="layui-form x-center" hidden="hidden">
    <form class="layui-form"   >
        <div class="layui-form-item">
            <label class="layui-form-label">应用名称：</label>
            <div class="layui-input-block" >
                <input type="hidden" name="Id" value="">
                <input type="text" name="AppName" value="${param.routeName}" maxlength="11" required lay-verify="required" placeholder="请输应用名" autocomplete="off"  style="width:80%;" class="layui-input">
            </div>
        </div>
    </form>
</form>
</body>

</html>