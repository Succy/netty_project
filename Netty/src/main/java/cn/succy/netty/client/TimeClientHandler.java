package cn.succy.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 时间服务客户端处理类
 *
 * @author Succy
 * @date 2017-01-28 12:07
 **/

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    //private final ByteBuf message;
    private byte[] req;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ByteBuf recvBuf = (ByteBuf) msg;
        //byte[] recvBody = new byte[recvBuf.readableBytes()];
        //recvBuf.readBytes(recvBody);
        //String body = new String(recvBody, "utf-8");
        String body = (String) msg;
        System.out.println("Now is " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
