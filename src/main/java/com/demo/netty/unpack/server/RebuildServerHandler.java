package com.demo.netty.unpack.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class RebuildServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int index = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //将接收到的客户端Bytebuf数据转成字符串，并打印出来
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("服务端接收到消息：" + message + " ");
        System.out.println("服务端接收到第" + (++index) + "条消息");
        //回复给客户端uuid
        String uuid = UUID.randomUUID().toString();
        ByteBuf resp = Unpooled.copiedBuffer(uuid,Charset.forName("utf-8"));
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
