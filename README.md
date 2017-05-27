# websocket
基于netty的定时推送websocket服务器
```
一个html页面通过js代码连接到socket服务器后，服务器定时向该html发送数据
```

1. 运行代码

  > 1. git clone [https://github.com/scutuyu/websocket.git](https://github.com/scutuyu/websocket.git)

  > 2. 使用idea打开项目

  > 3. 下载完依赖后，右键运行Server.java
```
Web socket server started at port 18080.
```
> 4. 用浏览器打开src/main/resources
/test.html, 并打开浏览器的调试模式，将会看到服务发送给客户端的推送数据
```
打开WebSocket服务正常，浏览器支持WebSocket!     test.html:20
Sat May 27 15:31:48 CST 2017                test.html:17
Sat May 27 15:31:50 CST 2017                test.html:17
Sat May 27 15:31:52 CST 2017                test.html:17
Sat May 27 15:31:54 CST 2017                test.html:17
Sat May 27 15:31:56 CST 2017                test.html:17
Sat May 27 15:31:58 CST 2017                test.html:17
```
2. [<strong>coding](https://github.com/scutuyu/websocket/blob/master/howtocode.md)
