<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/5
  Time: 8:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Demo Chat</title>
    <link href="js/bootstrap.css" rel="stylesheet">
    <style>
        body {
            padding:20px;
        }
        #console {
            height: 400px;
            overflow: auto;
        }
        .username-msg {color:orange;}
        .connect-msg {color:green;}
        .disconnect-msg {color:red;}
        .send-msg {color:#888}
    </style>
    <script src="js/socket.io.js"></script>
    <script src="js/moment.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script>
        var clientid = 'testclient1';
        var targetClientId= 'testclient2';

        var socket =  io.connect('http://localhost:8081?clientid='+clientid);

        socket.on('connect', function() {
            output('<span class="connect-msg">Client has connected to the server!</span>');
        });

        socket.on('messageevent', function(data) {
            output('<span class="username-msg">' + data.sourceClientId + ':</span> ' + data.msgContent+' content:'+data.Content);
        });

        socket.on('disconnect', function() {
            output('<span class="disconnect-msg">The client has disconnected!</span>');
        });

        function sendDisconnect() {
            socket.disconnect();
        }

        function sendMessage() {
            var message = $('#msg').val();
            $('#msg').val('');

            var jsonObject = {sourceClientId: clientid,
                targetClientId: targetClientId,
                msgType: 'chat',
                msgContent: message};
            socket.emit('messageevent', jsonObject);
        }

        function output(message) {
            var currentTime = "<span class='time'>" +  moment().format('HH:mm:ss.SSS') + "</span>";
            var element = $("<div>" + currentTime + " " + message + "</div>");
            $('#console').prepend(element);
        }

        $(document).keydown(function(e){
            if(e.keyCode == 13) {
                $('#send').click();
            }
        });
    </script>
</head>
<body>
<h1>Netty-socketio Demo Chat</h1>
<br/>
<div id="console" class="well">
</div>
<form class="well form-inline" οnsubmit="return false;">
    <input id="msg" class="input-xlarge" type="text" placeholder="Type something..."/>
    <button type="button" onClick="sendMessage()" class="btn" id="send">Send</button>
    <button type="button" onClick="sendDisconnect()" class="btn">Disconnect</button>
</form>
</body>
</html>