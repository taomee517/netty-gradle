package com.demo.netty.unpack.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class RespHandler extends SimpleChannelInboundHandler<Msg> {
    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        String inputMsg = new String(content, Charset.forName("utf-8"));
        System.out.println("客户端接收到第" + (++count) + "条消息:" + inputMsg);
        String uuid = UUID.randomUUID().toString();
        Msg outputMsg = new Msg();
        outputMsg.setContent(uuid.getBytes(Charset.forName("utf-8")));
        outputMsg.setLength(uuid.getBytes(Charset.forName("utf-8")).length);
        ctx.writeAndFlush(outputMsg);
    }
}
