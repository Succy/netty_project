package cn.succy.bio.pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * 处理客户端的输出线程类
 *
 * @author Succy
 * @date 2017-01-25 10:02
 **/

public class ClientPrintHandler implements Runnable {
    private Socket socket;

    public ClientPrintHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader recvBuf = null;
        try {
            recvBuf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data;
            while ((data = recvBuf.readLine()) != null) {
                System.out.println("接收到服务器返回： " + data);
            }
        } catch (SocketException exp) {
            if("Connection reset".equals(exp.getMessage())){
                System.out.println("Msg: " + exp.getMessage());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (recvBuf != null) {
                    recvBuf.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
