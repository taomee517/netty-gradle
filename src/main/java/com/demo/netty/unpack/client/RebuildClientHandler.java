package com.demo.netty.unpack.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class RebuildClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int index = 0;
    private String poem ="If you shed tears when you miss the sun," +
            "you also miss the stars,let life be beautiful like summer " +
            "flowers and death like autumn leaves";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String message = new String(bytes, Charset.forName("utf-8"));
        System.out.println("客户接收到消息：" + message);
        System.out.println("客户端接收到第" + (++index) + "条消息");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String[] phases = poem.split(",");
        for(int i=0;i<phases.length;i++){
            ByteBuf buf = Unpooled.copiedBuffer(phases[i],Charset.forName("utf-8"));
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
