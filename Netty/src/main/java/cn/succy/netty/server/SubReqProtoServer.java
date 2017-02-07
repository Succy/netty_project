package cn.succy.netty.server;

import cn.succy.netty.protobuf.SubscribeReqProto;
import cn.succy.netty.server.handler.SubReqProtoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 使用protobuf的图书订购服务器端
 *
 * @author Succy
 * @date 2017-02-07 15:49
 */
public class SubReqProtoServer {
    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            // 加入解决protobuf读半包的解码器
                            sc.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            // 加入protobuf的解码器，但是要指定对于哪个message的
                            sc.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
                            sc.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            // 返回客户端的message也要编码的，因此设置protobuf的编码器
                            sc.pipeline().addLast(new ProtobufEncoder());
                            // 添加服务端处理器
                            sc.pipeline().addLast(new SubReqProtoServerHandler());
                        }
                    });

            ChannelFuture cf = sb.bind(port).sync();
            cf.channel().closeFuture().sync();

        } finally {
            // 关闭资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new SubReqProtoServer().bind(9856);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
