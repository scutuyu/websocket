package com.tuyu.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Timer;

/**
 * Created by tuyu on 5/26/17.
 */
@ChannelHandler.Sharable
public class MyChannelHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Timer timer = new Timer();



    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception { // (1)
        Channel incoming = ctx.channel();
//        incoming.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
        timer.schedule(new MyTimerTask(ctx), 0, 2000);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        incoming.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        incoming.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
            throws Exception {
        Channel incoming = ctx.channel();
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}

