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
    <title>区域信息管理</title>
    <link rel="stylesheet" href="./plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="./plugins/common.css"/>
    <style>
        .x-item {
            margin-bottom: 0px;
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="wrap-container clearfix">
    <div class="layui-form-pane" style="margin-top: 15px;">
        <div class="layui-form-item  x-item">
            <div class="layui-input-inline">
                <input type="text" id="edt-search" required lay-verify="required" placeholder="请输入应用名称" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-input-inline" style="width:80px">
                <button class="layui-btn" lay-filter="sreach" id="btn-search"><i class="layui-icon"></i></button><label id="parentIframe"></label>
            </div>
        </div>
    </div>
    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <button class="layui-btn"  type="button" lay-event="expand">全部展开</button>
            <button class="layui-btn"  type="button" lay-event="fold">全部折叠</button>
            <button class="layui-btn layui-btn-normal" type="button" lay-event="add">新增</button>
        </div>
    </script>
    <table id="auth-table" class="layui-table" lay-filter="auth-table"></table>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
</div>
<%--<script src="assets/layui/layui.js"></script>--%>
<script src="./js/jquery.min.js"></script>
<script src="./js/jquery.form.js"></script>
<script src="./plugins/layui/layui.js"></script>
<script src="./plugins/admin/js/common.js" ></script>
<script>
    var treetable=null;
    layui.config({
        base: 'module/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['table', 'form', 'element', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var form = layui.form;
        var element = layui.element;
        treetable = layui.treetable;
// 渲染表格
        var renderTable = function () {
            // 渲染表格
            layer.load(2);
            treetable.render({
                treeColIndex: 1,
                treeSpid: 0,
                treeIdName: 'd_id',
                treePidName: 'd_pid',
                elem: '#auth-table',
                url: 'AreaInfo/json',
                treeDefaultClose: true,
                toolbar: '#toolbarDemo',
                page: true,
                cols: [[
                    {type: 'numbers'},
                    {field: 'name', minWidth: 200, title: '区域名'},
                    {field: 'd_id', title: '区域代码'},

                    {
                        field: 'arealevel', width: 80, align: 'center', templet: function (d) {
                            if (d.arealevel == 1) {
                                return '<span class="layui-badge layui-bg-orange">省</span>';
                            }
                            if (d.arealevel == 2) {
                                return '<span class="layui-badge layui-bg-blue">市</span>';
                            }
                            if (d.arealevel == 3) {
                                return '<span class="layui-badge-rim layui-bg-green">区县</span>';
                            }
                            if (d.arealevel == 4) {
                                return '<span class="layui-badge-rim">乡镇</span>';
                            } else {
                                return '<span class="layui-badge-rim">乡镇</span>';
                            }
                        }, title: '类型'
                    },
                    , {field: 'id', title: '操作', toolbar: '#barDemo'}
                ]],
                done: function () {
                    $('.layui-table').find('th').each(function (index,element) {
                        if($(this).attr('data-field')>=0){
                            $(this).find('div').css('width',$(this).width());
                        }
                    });
                    layer.closeAll('loading');
                }
            });
        }
        renderTable();
        $('#parentIframe').on('click', function(){
            renderTable();
        });
        $('#btn-search').click(function () {
            var keyword = $('#edt-search').val();
            var searchCount = 0;
            $('#auth-table').next('.treeTable').find('.layui-table-body tbody tr td').each(function () {
                $(this).css('background-color', 'transparent');
                var text = $(this).text();
                if (keyword != '' && text.indexOf(keyword) >= 0) {
                    $(this).css('background-color', 'rgba(250,230,160,0.5)');
                    if (searchCount == 0) {
                        treetable.expandAll('#auth-table');
                        $('html,body').stop(true);
                        $('html,body').animate({scrollTop: $(this).offset().top - 150}, 500);
                    }
                    searchCount++;
                }
            });
            if (keyword == '') {
                layer.msg("请输入搜索内容", {icon: 5});
            } else if (searchCount == 0) {
                layer.msg("没有匹配结果", {icon: 5});
            }
        });
    });
    var table = null;
    layui.use('table', function () {
        table = layui.table;
        //监听工具条
        table.on('tool(auth-table)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    del(data.d_id);
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                update(data.name,data.d_id);
            }
        });
        //头工具栏事件
        table.on('toolbar(auth-table)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                //新增
                case 'add':
                    layer.open({
                        title:"区域添加",
                        type: 2,
                        area: ['480px', '350px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: '/AreaInfo/add'
                    });
                   break;
                case 'expand':
                    treetable.expandAll('#auth-table');
                    break;
                case 'fold':
                    treetable.foldAll('#auth-table');
                    break;
            };
        });


    });
</script>
<script>
    function  update(appname,id) {
        $("input[name=AreaName]").val(appname);
        $("input[name=Id]").val(id);
        layer.open({
            type: 1
            , title: "修改区域"
            , content: $("#bj")
            , area: ['500px', '320px']
            , btn: ['修改', '取消'] //只是为了演示
            , yes: function () {
                if($("input[name=AreaName]").val()==undefined||$("input[name=AreaName]").val()==null||$("input[name=AreaName]").val()=="")
                {
                    layer.alert("请输入区域名",{
                        icon:2,
                        title:'错误'
                    });
                    return false;
                }
                $.ajax({
                    type: "post"
                    , url: "/AreaInfo/update"
                    , dataType: 'json'
                    ,contentType : 'application/json;charset=utf-8'
                    ,data:JSON.stringify({
                        areaname:$("input[name=AreaName]").val()
                        ,code:$("input[name=Id]").val()
                    })
                    , success: function (data) {
                        layer.msg(data.msg, {time: 1000});
                        if (data.code == 3) {
                            setTimeout(function () {
                                esc();
                                $('#parentIframe').click();
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
            , url: "/AreaInfo/del/" + routeNo
            , dataType: 'json'
            , success: function (data) {
                layer.msg(data.msg, {time: 1000});
                if (data.code == 3) {
                    $('#parentIframe').click();
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
                <input type="text" name="AreaName" value="${param.routeName}" maxlength="11" required lay-verify="required" placeholder="请输应用名" autocomplete="off"  style="width:80%;" class="layui-input">
            </div>
        </div>
    </form>
</form>
</body>

</html>