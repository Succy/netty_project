package cn.succy.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * EchoServer端
 *
 * @author Succy
 * @date 2017-02-04 20:34
 **/

public class EchoServer {
    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workGroup);
            sb.channel(NioServerSocketChannel.class);
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            sb.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                    sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                    sc.pipeline().addLast(new StringDecoder());
                    sc.pipeline().addLast(new EchoServerHandler());
                }
            });
            ChannelFuture cf = sb.bind(port).sync();
            cf.channel().closeFuture().sync();
        }finally {
            // 优雅地关闭资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }// end of bind(port)

    public static void main(String[] args) {
        try {
            new EchoServer().bind(9856);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
