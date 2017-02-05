package cn.succy.bio.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Echo服务器
 *
 * @author Succy
 * @date 2017-01-24 23:50
 **/

public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void service() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            EchoServerHandlerExecutePool handlerExecutePool = new EchoServerHandlerExecutePool(50, 1000);
            while (true) {
                Socket socket = server.accept();
                handlerExecutePool.execute(new EchoHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new EchoServer(9875).service();
    }

}

