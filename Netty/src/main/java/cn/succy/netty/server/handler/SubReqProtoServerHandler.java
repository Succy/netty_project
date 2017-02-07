package cn.succy.netty.server.handler;

import cn.succy.netty.protobuf.SubscribeReqProto;
import cn.succy.netty.protobuf.SubscribeReqProto.SubscribeReq;
import cn.succy.netty.protobuf.SubscribeRespProto;
import cn.succy.netty.protobuf.SubscribeRespProto.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 使用protobuf的图书订购服务器端处理器
 *
 * @author Succy
 * @date 2017-02-07 16:02
 */
public class SubReqProtoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 由于在接收到数据的时候，使用protobuf的ProtobufDecoder对数据进行解码，因此，在这里读取到的数据
        // 已经是SubReqProto类型的了
        SubscribeReqProto.SubscribeReq req = (SubscribeReq) msg;

        // 对用户名进行校验一下
        if ("Succy".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Accept client request, msg: [\n" + req.toString() + "]");

            SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
            builder.setSubReqID(req.getSubReqID());
            builder.setRespCode(0);
            builder.setDesc("成功订购一本《Netty in action》");
            SubscribeResp resp = builder.build();
            ctx.writeAndFlush(resp);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("catch exception msg=" + cause.getMessage());
        ctx.close();
    }


}
