package com.demo.netty.unpack.codec;

import com.demo.netty.common.DefaultValue;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class ActiveHandler extends SimpleChannelInboundHandler<Msg> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
            Msg msg = new Msg();
            int length = DefaultValue.DEFAULT_FIRST_MSG.getBytes(Charset.forName("utf-8")).length;
            byte[] content = DefaultValue.DEFAULT_FIRST_MSG.getBytes(Charset.forName("utf-8"));
            msg.setLength(length);
            msg.setContent(content);
            ctx.writeAndFlush(msg);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        String m = new String(content, Charset.forName("utf-8"));
        System.out.println("客户端接收到第" + (++count) + "条消息" + m);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
