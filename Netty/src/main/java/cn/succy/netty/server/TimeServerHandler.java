package cn.succy.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 处理IO操作的服务器处理类
 * 注：netty4.x中，channelRead等方法放在了ChannelInboundHandlerAdapter抽象类中
 *
 * @author Succy
 * @date 2017-01-26 15:27
 **/

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 把接收到的对象转换成ByteBuf
        //ByteBuf recvBuf = (ByteBuf) msg;
        //// 将缓冲区中的数据读取到byte数组
        //byte[] req = new byte[recvBuf.readableBytes()];
        //recvBuf.readBytes(req);
        //
        //String reqBody = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());
        String reqBody = (String) msg;

        System.out.println("接收到服务器数据： " + reqBody + " count " + ++count);

        // 往回写时间
        String curTime = "QUERY TIME ORDER".equalsIgnoreCase(reqBody) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        curTime = curTime + System.getProperty("line.separator");
        ByteBuf sendBuf = Unpooled.copiedBuffer(curTime.getBytes());

        System.out.println(curTime);
        ctx.write(sendBuf);
        //ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
