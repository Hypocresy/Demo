package com.netty.demo.client;

import cn.hutool.core.util.StrUtil;
import com.netty.demo.server.NettServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/4/23 14:49
 * @since 0.0.1
 * netty 客户端
 */
public class NettClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(1);
        try{
            bootstrap.group(loopGroup);
            bootstrap.channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 9011).sync();
            Channel channel = sync.channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                String line = reader.readLine();
                if(StrUtil.isEmpty(line)){
                    break;
                }else if("exit".equals(line.toLowerCase())){
                    channel.closeFuture();
                    break;
                }else {
                    System.out.println("发送数据： "+line);
                    channel.writeAndFlush(line+"\r\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
        }

    }
}
