package cn.succy.netty.client;

import cn.succy.netty.bean.SubscribeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * SubReq客户端处理器
 *
 * @author Succy
 * @date 2017-02-05 20:50
 **/

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(req(i));
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive response from Server msg=[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private SubscribeReq req(int subReqID) {
        SubscribeReq req = new SubscribeReq();
        req.setSubReqID(subReqID);
        req.setAddress("广州市海珠区工业大道北");
        req.setProductName("Netty in action");
        req.setPhoneNum("13078077563");
        req.setUserName("Succy");
        return req;
    }
}
