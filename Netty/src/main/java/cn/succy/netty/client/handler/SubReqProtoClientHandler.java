package cn.succy.netty.client.handler;

import cn.succy.netty.protobuf.SubscribeReqProto;
import cn.succy.netty.protobuf.SubscribeReqProto.SubscribeReq;
import cn.succy.netty.protobuf.SubscribeRespProto.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用protobuf的图书订购客户端处理器
 *
 * @author Succy
 * @date 2017/2/7 16:34
 */
public class SubReqProtoClientHandler extends ChannelInboundHandlerAdapter {

    public SubReqProtoClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SubscribeReq subReq = createSubReq();
        ctx.writeAndFlush(subReq);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeResp resp = (SubscribeResp) msg;
        System.out.println("Server response msg = " + resp.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("catch exception msg=" + cause.getMessage());
        ctx.close();
    }

    private static SubscribeReqProto.SubscribeReq createSubReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setPhoneNum("13078077563");
        builder.setProductName("Netty in action");
        builder.setUserName("Succy");
        List<String> addressList = new ArrayList<>();
        addressList.add("广州市海珠区工业大道");
        builder.addAllAddress(addressList);

        return builder.build();
    }
}
