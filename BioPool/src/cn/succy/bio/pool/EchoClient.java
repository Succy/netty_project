package cn.succy.bio.pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 *
 * @author Succy
 * @date 2017-01-25 0:08
 **/

public class EchoClient {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader sendBuf = null;

        PrintWriter pw = null;
        try {
            socket = new Socket("127.0.0.1", 9999);

            // 开启读线程
            new Thread(new ClientPrintHandler(socket)).start();
            sendBuf = new BufferedReader(new InputStreamReader(System.in));

            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String info = "";
            while (!"exit".equals(info)) {
                info = sendBuf.readLine();
                pw.print(info);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null)
                    pw.close();
                if (sendBuf != null)
                    sendBuf.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
