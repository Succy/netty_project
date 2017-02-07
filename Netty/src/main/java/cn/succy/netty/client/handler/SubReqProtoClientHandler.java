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
        // 循环多次，营造压力测试，造成TCP粘包，测试服务器读半包解析
        for (int i = 0; i < 10; i++) {
            SubscribeReq subReq = createSubReq(i);
            ctx.write(subReq);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeResp resp = (SubscribeResp) msg;
        System.out.println("Server response msg: [\n" + resp.toString() + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("catch exception msg=" + cause.getMessage());
        ctx.close();
    }

    private static SubscribeReqProto.SubscribeReq createSubReq(int subReqId) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(subReqId);
        builder.setPhoneNum("13078077563");
        builder.setProductName("Netty in action");
        builder.setUserName("Succy");
        List<String> addressList = new ArrayList<>();
        addressList.add("GuangZhou HaiZhu GongYeDaDao");
        builder.addAllAddress(addressList);

        return builder.build();
    }
}
