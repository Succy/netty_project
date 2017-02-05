package cn.succy.bio.pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 处理服务器请求的线程处理类
 *
 * @author Succy
 * @date 2017-01-24 23:55
 **/

public class EchoHandler implements Runnable {
    private Socket socket;

    public EchoHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(socket.getInetAddress().getHostAddress() + " : " + line);
                pw.println(line);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null)
                    pw.close();
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
