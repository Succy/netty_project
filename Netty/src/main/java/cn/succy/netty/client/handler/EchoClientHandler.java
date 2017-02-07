package cn.succy.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * EchoClient处理器
 *
 * @author Succy
 * @date 2017-02-04 22:59
 **/

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private int count;
    private byte[] buf;

    public EchoClientHandler() {
        buf = "Hello, Welcome to Netty.$_".getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf sendBuf;
        for (int i = 0; i < 10; i++) {
            sendBuf = Unpooled.buffer(buf.length);
            sendBuf.writeBytes(buf);
            ctx.writeAndFlush(sendBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("the " + ++count + " time receive from server; msg=" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
