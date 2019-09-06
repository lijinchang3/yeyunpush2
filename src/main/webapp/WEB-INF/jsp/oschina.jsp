<%--
  Created by IntelliJ IDEA.
  User: icss
  Date: 2018/8/2
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../js/socket.io.js"></script>
</head>
<body>

</body>
<script >

    var username = 'username';
    var socket = io.connect('http://localhost:9092?no=' + username);

    socket.on('connect', function () {
        console.log('连接')
    });

    socket.on('runningTask', function (data) {
        console.log("收到全服数据")
        console.log(data);
    });


    socket.on('taskResult', function (data) {
        console.log("收到个人数据")
        console.log(data);

    });

    // 可以是任意类型的数据，这里用了一个json对象，后端有对应的实体类
    var jsonObject = {
        "username": "username1",
        "to": 2
    };
    socket.emit('messageevent', jsonObject);


    socket.on('disconnect', function () {
        console.log("断开")
    });

</script>
</html>
