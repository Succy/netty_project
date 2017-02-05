package cn.succy.netty.server;

import cn.succy.netty.bean.SubscribeReq;
import cn.succy.netty.bean.SubscribeResp;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * SubReq服务器处理器
 *
 * @author Succy
 * @date 2017-02-05 15:28
 **/
@Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("Succy".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscribe req=[" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    /**
     * 构造响应消息
     * @param subReqID 请求的订单id
     * @return 返回构造好的响应消息实体
     */
    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("subscribe success!");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
