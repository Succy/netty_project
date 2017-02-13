package cn.succy.rpc.comm;

import cn.succy.rpc.comm.util.StringUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 客户端相关配置参数类
 *
 * @author Succy
 * @date 2017-02-12 20:32
 **/

public class ClientConfig extends Config {
    // Client
    private String remoteHost;
    private int remotePort;


    public ClientConfig(){
    }

    public ClientConfig(String configFile) {
        super.setConfigFile(configFile);
    }

    @Override
    protected boolean assignParam(JSONObject obj) {
        if (obj == null) {
            logger.error("jsonObj is null,assignment failure.");
            return false;
        }
        // ---- start assignment client param
        JSONObject client = obj.getJSONObject(ConfigDef.Client.TAG);
        if(client == null) {
            logger.error("can not resolve the required field: client");
            return false;
        }

        if(StringUtil.isEmpty(client.getString(ConfigDef.Client.HOST))) {
            logger.error("can not resolve the required field: client.host");
            return false;
        }
        remoteHost = client.getString(ConfigDef.Client.HOST);
        logger.debug("assignment remoteHost ==> %s", remoteHost);

        if (client.getIntValue(ConfigDef.Client.PORT) <= 0) {
            logger.error("can not resolve the required field: client.port");
            return false;
        }
        remotePort = client.getIntValue(ConfigDef.Client.PORT);
        logger.debug("assignment remotePort ==> %d", remotePort);

        return true;
    }

    // getter
    public String getRemoteHost() {
        return remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    @Override
    public String toString() {
        return "ClientConfig{" +
                "remoteHost='" + remoteHost + '\'' +
                ", remotePort=" + remotePort +
                '}';
    }
}
