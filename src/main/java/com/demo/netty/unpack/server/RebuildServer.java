package com.demo.netty.unpack.server;

import com.demo.netty.common.DefaultValue;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RebuildServer {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //添加eventloop组，设置socketChannel类型，指定初始化器（中的各种handler）
            bootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
                    .childHandler(new RebuildServerChannelInitializer());
            ChannelFuture future = bootstrap.bind(DefaultValue.DEFAULT_TEST_PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
