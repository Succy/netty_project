package cn.succy.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 时间服务客户端
 *
 * @author Succy
 * @date 2017-01-28 11:52
 **/

public class TimeClient {

    public void connect(String host, int port) throws Exception {
        // 创建客户端线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            sc.pipeline().addLast(new StringDecoder());
                            sc.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            // 发起连接请求
            ChannelFuture cf = bs.connect(host, port).sync();
            // 等待客户端链路关闭
            cf.channel().closeFuture().sync();
        } finally {
            // 退出，释放资源
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new TimeClient().connect("127.0.0.1", 9999);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
