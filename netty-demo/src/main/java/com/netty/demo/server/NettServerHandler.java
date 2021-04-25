package com.netty.demo.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/4/23 10:15
 * @since 0.0.1
 */
public class NettServerHandler extends SimpleChannelInboundHandler<String> {
  private EventLoopGroup eventLoopGroup;

    public  NettServerHandler(EventLoopGroup eventLoopGroup){
        this.eventLoopGroup  =eventLoopGroup;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("建立连接成功！！ "+ctx.channel().localAddress());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String o)  {
//        System.out.println("服务端收到消息： "+o);
        Channel channel = channelHandlerContext.channel();
        eventLoopGroup.forEach(ch->{
            if(channel != ch){

            }else {

            }
        });

    }

}
