package com.netty.demo.server;

import cn.hutool.core.util.StrUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * @author hy
 * @blame Development Group
 * @date 2021/4/23 9:21
 * @since 0.0.1
 * netty 服务端
 */
public class NettServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup(10);
        try {
            serverBootstrap.group(bossGroup, eventExecutors);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel  ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new NettServerHandler(eventExecutors));
                        }
                    });
            ChannelFuture f = serverBootstrap.bind(9011).sync();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                String line = reader.readLine();
                if(StrUtil.isEmpty(line)){
                    break;
                }else  if("exit".equals(line.toLowerCase())){
                    f.channel().closeFuture();
                }{
                    System.out.println("输入： "+line);
                }

           }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            eventExecutors.shutdownGracefully();
        }


    }
}
