1. 服务端构建一个ServerBoottrap和两个EventLoopGroup
```
ServerBoottrap bootstrap = new ServerBoottrap();
EventLoopGroup boss = new NioEventLoopGroup();
EventLoopGroup worker = new NioEventLoopGroup();
```
2. 配置bootstrap
```
bootstrap
  .group(boss, worker)       //将两个EventLoopGroup加入到bootstrap中
  .option(ChannelOption.SO_BACKLOG, 1024)  //我猜估计是配置一个参数，大小是1024，单位不详，查看源码应该能知道，我是新手，以后再完善
  .childOption(ChannelOption.SO_KEEPALIVE, true)  //我猜是配置socket长连接的
  .childHandler(new MyInitializer());    //这个是初始管道参数,MyInitializer这个类是我自己写的，它继承自ChannelInitializer<SocketChannel>，泛型是SocketChannel,后面会讲到如何配置MyInitializer类
```
3. 将bootstrap与端口绑定
```
ChannelFuture future = bootstrap.bind(port).sync();   //为什么在后面调用.sync(),我也不太清楚，毕竟新手嘛，先知其然，再知其所以然，以后再完善
future.channel().closeFuture().sync();    //到此，服务端就写完了，然后就是写配置管道的类MyInitializer
```
4.配置MyInitializer,继承ChannelInitializer<SocketChannel>,实现initChannel
4.配置MyInitializer,继承ChannelInitializer<SocketChannel>,实现initChannel(SocketChannel socketChannel)方法
```
protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();

        p.addLast(new HttpServerCodec());  //HttpServerCodec类会处理数据的解码和编码等工作
        p.addLast(new HttpObjectAggregator(64*1024));  //配置一些参数，具体我不太清楚
        p.addLast(new ChunkedWriteHandler());  //配置一些块大小的参数，具体不详
        p.addLast(new HttpRequestHandler("/ws"));   //HttpRequestHandler类是我自己写的类，它继承自SimpleChannelInboundHandler<FullHttpRequest>，重写了channelRead0方法和exceptionCaught方法，具体看代码，另外需要传入客户端连接服务端websocket的路径，根路径是/ws
        p.addLast(new WebSocketServerProtocolHandler("/ws"));   // 处理http协议方面的东西
        p.addLast(new MyChannelHandler());   //MyChannelHandler类是我自己写的类，该类继承自SimpleChannelInboundHandler<TextWebSocketFrame>，最重要的是重写channelRead0方法，也可以重写其他的方法，在channelRead0可以实现向客户端发送数据的功能
    }
```
5. 用jdk自带的timer向客户端定时发送数据
```
public class MyTimerTask extends TimerTask {
    ChannelHandlerContext ctx;

    public MyTimerTask(){

    }

    public MyTimerTask(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    @Override
    public void run() {
        String dateStr = new Date().toString();
        Channel channel = ctx.channel();
        if (channel.isOpen()){
            channel.writeAndFlush(new TextWebSocketFrame(dateStr));
        }else {
            System.out.println("channel closed,so cancel task.");
            cancel();
        }

    }
}
```
6. 在channelRead0方法中用timer定时调用MyTimerTask类
```
timer.schedule(new MyTimerTask(ctx), 0, 2000);  //延时为0s，没2s发送一次数据
```
