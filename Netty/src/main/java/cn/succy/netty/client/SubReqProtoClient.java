package cn.succy.netty.client;

import cn.succy.netty.client.handler.SubReqProtoClientHandler;
import cn.succy.netty.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 使用protobuf的图书订购客户端
 *
 * @author Succy
 * @date 2017/2/7 16:25
 */
public class SubReqProtoClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            // 客户端的解码器，要解码的对象是服务器返回的响应message
                            sc.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
                            sc.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            // 客户端的编码器要编码的是请求message
                            sc.pipeline().addLast(new ProtobufEncoder());
                            sc.pipeline().addLast(new SubReqProtoClientHandler());
                        }
                    });

            ChannelFuture cf = b.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        } finally {
            // 关闭释放资源
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new SubReqProtoClient().connect("127.0.0.1", 9856);
    }
}
