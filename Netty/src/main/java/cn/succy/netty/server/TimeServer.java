package cn.succy.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 第一个nettydemo，时间服务器的实现。
 *
 * @author Succy
 * @date 2017-01-26 14:57
 **/

public class TimeServer {
    /**
     * 绑定端口
     * @param port 要绑定的端口
     * @throws Exception
     */
    public void bind(int port) throws  Exception {
        // 创建服务器的线程组,创建两个的原因是，一个用来接收客户端的连接，一个用来进行网络读写
        // NioEventLoopGroup()其实就是一组nio的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGrop = new NioEventLoopGroup();

        try{
            // 创建服务启动器
            ServerBootstrap sb = new ServerBootstrap();
            // 配置服务器线程组
            sb.group(bossGroup, workGrop)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)// 设置backlog为1024
                    .childHandler(new ChildChannelHandler());// 绑定io事件的处理类

            // 绑定端口，同步等待成功；sync()是一个同步阻塞的
            // ChannelFuture用于异步操作通知的回调
            ChannelFuture cf = sb.bind(port).sync();
            // 等待服务器端口关闭，服务器链路关闭之后再结束main线程
            cf.channel().closeFuture().sync();
        }finally {
            // 退出，释放线程池资源，我们不用关心到底开启多少资源，反正netty会帮我们都释放
            bossGroup.shutdownGracefully();
            workGrop.shutdownGracefully();
        }
    }// end of bind()

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel sc) throws Exception {
            // 配置LineBaseFrameDecoder的作用是，把接收到的数据包以\n或者\r\n作为分隔，每一段数据作为单独的
            // 一个请求，解决tcp粘包的问题。
            // StringDecoder是把流转变成文本，当执行到TimeServerHandler的时候，已经把发送过来的数据拆分成一
            // 个个独立的数据包了，每一个数据包都会调用一个channelRead方法进行读取，这样就可以达到单独处理
            // 拆分出的每一个数据包了。
            sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
            sc.pipeline().addLast(new StringDecoder());
            sc.pipeline().addLast(new TimeServerHandler());// 把服务器处理类当成参数传递，要放ChannelHandler类型
        }
    }

    public static void main(String[] args) {
        try {
            new TimeServer().bind(9999);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
