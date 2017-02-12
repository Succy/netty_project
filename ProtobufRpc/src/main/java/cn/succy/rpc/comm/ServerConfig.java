package cn.succy.rpc.comm;

import com.alibaba.fastjson.JSONObject;

/**
 * 服务器相关参数配置类
 *
 * @author Succy
 * @date 2017-02-12 20:31
 **/

public class ServerConfig extends Config {
    // Server
    private String serverHost;
    private int serverPort;
    private String serverName;
    // DB
    private String dbUrl;
    private int dbPort;
    private String dbName;
    private String dbUser;
    private String dbPwd;
    private String dbDataSource;

    // Cache
    private String cacheHost;
    private int cachePort;

    public ServerConfig() {
    }

    public ServerConfig(String configFile) {
        super.setConfigFile(configFile);
    }

    @Override
    protected boolean assignParam(JSONObject obj) {
        if (obj == null) {
            logger.error("jsonObj is null,assignment failure.");
            return false;
        }
        // --- start assignment server param
        JSONObject server = obj.getJSONObject(ConfigDef.Server.TAG);
        if (server == null) {
            logger.error("can not resolve the required field: server");
            return false;
        }
        if (server.getString(ConfigDef.Server.SERVER_NAME) == null) {
            logger.error("can not resolve the required field: server.serverName");
            return false;
        }
        serverName = server.getString(ConfigDef.Server.SERVER_NAME);
        logger.debug("assignment serverName ==> %s", serverName);

        if (server.getString(ConfigDef.Server.HOST) == null) {
            logger.error("can not resolve the required field: server.host");
            return false;
        }
        serverHost = server.getString(ConfigDef.Server.HOST);
        logger.debug("assignment serverHost ==> %s", serverHost);

        if (server.getIntValue(ConfigDef.Server.PORT) <= 0) {
            logger.error("can not resolve the required field: server.port");
            return false;
        }
        serverPort = server.getIntValue(ConfigDef.Server.PORT);
        logger.debug("assignment serverPort ==> %d", serverPort);
        // ---- end of assignment server
        return true;
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerName() {
        return serverName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPwd() {
        return dbPwd;
    }

    public String getDbDataSource() {
        return dbDataSource;
    }

    public String getCacheHost() {
        return cacheHost;
    }

    public int getCachePort() {
        return cachePort;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                ", serverHost='" + serverHost + '\'' +
                ", serverPort=" + serverPort +
                ", serverName='" + serverName + '\'' +
                ", dbUrl='" + dbUrl + '\'' +
                ", dbPort=" + dbPort +
                ", dbName='" + dbName + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPwd='" + dbPwd + '\'' +
                ", dbDataSource='" + dbDataSource + '\'' +
                ", cacheHost='" + cacheHost + '\'' +
                ", cachePort=" + cachePort +
                '}';
    }
}
