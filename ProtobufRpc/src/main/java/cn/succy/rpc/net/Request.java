package cn.succy.rpc.net;

import cn.succy.rpc.comm.ServerConfig;

/**
 * @author Succy
 * @date 2017/2/13 17:51
 */
public class Request {
    private String remoteHost;// 要请求的远程主机ip
    private int remotePort;// 要请求的远程主机的端口号
    private String localHost;// 发起请求端的ip
    private int localPort;// 发起请求端的端口号

    private ServerConfig config;

    public Request() {

    }

    public Request(ServerConfig config) {
        this.config = config;
    }

    private void init() {

    }


    // getter and setter
    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getLocalHost() {
        return localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
}
