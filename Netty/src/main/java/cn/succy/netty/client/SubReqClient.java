package cn.succy.netty.client;

import cn.succy.netty.client.handler.SubReqClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * SubReq客户端
 *
 * @author Succy
 * @date 2017-02-05 20:34
 **/

public class SubReqClient {
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
                            sc.pipeline().addLast(new ObjectDecoder(1024,
                                    ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                            sc.pipeline().addLast(new ObjectEncoder());
                            sc.pipeline().addLast(new SubReqClientHandler());
                        }
                    });
            ChannelFuture cf = b.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }// end of connect(host, port)

    public static void main(String[] args) {
        try {
            new SubReqClient().connect("127.0.0.1", 9856);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
