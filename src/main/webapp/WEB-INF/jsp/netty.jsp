<%--
  Created by IntelliJ IDEA.
  User: icss
  Date: 2018/8/2
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>消息推送</title>
</head>
<body>

</body>
<script type="text/javascript" src="../../js/socket.io.js"></script>
<%--<script src="https://cdn.socket.io/socket.io-1.2.1.js"></script>--%>
<%--<script type="text/javascript" src="jquery-easyui-1.5.3/jquery.min.js"></script>--%>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
    <!-- 实时从指定查看是否有消息 -->
    var socket = io.connect('localhost:9092');
    socket.on('connect_msg',function(data){
        alert(data);
        var personInfo = JSON.parse(data);
        console.log(personInfo);
    });

</script>
</html>
