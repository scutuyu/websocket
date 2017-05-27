package com.tuyu.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created by tuyu on 5/27/17.
 */
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
