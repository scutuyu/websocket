package com.tuyu.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Hello world!
 *
 */
public class Server
{
    private static final int PORT = 18080;

    public void run(int port){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new MyInitializer());

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Web socket server started at port " + port + '.');
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main( String[] args )
    {
        int port = PORT;

        if (args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e){
                port = PORT;
                System.out.println("the port you input must be Integer.------> port error");
            }
        } else {
            port = PORT;
        }
        new Server().run(port);
    }
}
