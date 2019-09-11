<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/10
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../plugins/common.css"/>
    <style>
        .x-item {
            margin-bottom: 0px;
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div class="wrap-container clearfix">
    <form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">地区名</label>
        <div class="layui-input-inline">
            <input type="text" name="areaname" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">地区编码</label>
        <div class="layui-input-inline">
            <input type="text" name="code" lay-verify="required" lay-reqtext="地区编码是必填项，岂能为空？" placeholder="请输入区域代码" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
            <label for="" class="layui-form-label">上级区域</label>
            <div class="layui-input-inline">
                <input type="text"  name="parentcode" lay-verify="required" lay-reqtext="地区编码是必填项，岂能为空？"  placeholder="请输入上级区域代码" autocomplete="off" class="layui-input">
            </div>
    </div>
    <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="submits">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
      </div>
     </form>
</div>
<%--<script src="assets/layui/layui.js"></script>--%>
<script src="../js/jquery.min.js"></script>
<script src="../js/jquery.form.js"></script>
<script src="../plugins/layui/layui.js"></script>
<script src="../plugins/admin/js/common.js" ></script>
<script>
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $('#transmit').on('click', function(){
        parent.$('#parentIframe').text('我被改变了');
        parent.layer.tips('Look here', '#parentIframe', {time: 5000});
        parent.layer.close(index);
    });
    layui.use('form', function () {
       var form=layui.form;
        form.on('submit(submits)', function(data){
            layer.alert(JSON.stringify(data.field));
            $.ajax({
        url:"/AreaInfo/AddEntity",
        async: false,
        type:"POST",
        dataType: 'json',
        contentType : 'application/json;charset=utf-8',
        data:JSON.stringify(data.field),
        success: function(data){
            //parent.window.renderTable();
            parent.$('#parentIframe').click()
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    })});
        return false;
    });
</script>
</body>
</html>