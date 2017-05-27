package com.tuyu.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by tuyu on 5/26/17.
 */
public class MyInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();

        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(64*1024));
        p.addLast(new ChunkedWriteHandler());
        p.addLast(new HttpRequestHandler("/ws"));
        p.addLast(new WebSocketServerProtocolHandler("/ws"));
        p.addLast(new MyChannelHandler());
    }
}
