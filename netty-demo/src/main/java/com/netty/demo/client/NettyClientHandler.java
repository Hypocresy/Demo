package com.netty.demo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hy
 * @blame Development Group
 * @date 2021/4/23 14:51
 * @since 0.0.1
 * 说明
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("得到数据： "+s);
    }
}
