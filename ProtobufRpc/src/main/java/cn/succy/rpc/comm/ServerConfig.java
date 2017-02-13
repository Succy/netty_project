package cn.succy.rpc.comm;

import cn.succy.rpc.comm.util.StringUtil;
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

    // has [Server|DB|Cache] default = false
    private boolean hasDB = false;
    private boolean hasCache = false;

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
        // server这个字段一定要配置的
        if (server == null) {
            logger.error("can not resolve the required field: server");
            return false;
        }
        if (StringUtil.isEmpty(server.getString(ConfigDef.Server.SERVER_NAME))) {
            logger.error("can not resolve the required field: server.serverName");
            return false;
        }
        serverName = server.getString(ConfigDef.Server.SERVER_NAME);
        logger.debug("assignment serverName ==> %s", serverName);

        if (StringUtil.isEmpty(server.getString(ConfigDef.Server.HOST))) {
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

        // ---- start assignment db param
        JSONObject db = obj.getJSONObject(ConfigDef.DB.TAG);
        // db这个字段是可选的
        if (db != null) {
            hasDB = true;
            if (StringUtil.isEmpty(db.getString(ConfigDef.DB.URL))) {
                logger.error("can not resolve the required field: db.url");
                return false;
            }
            dbUrl = db.getString(ConfigDef.DB.URL);
            logger.debug("assignment dbUrl ==> %s", dbUrl);

            if (db.getIntValue(ConfigDef.DB.PORT) <= 0) {
                logger.error("can not resolve the required field: db.port");
                return false;
            }
            dbPort = db.getIntValue(ConfigDef.DB.PORT);
            logger.debug("assignment dbPort ==> %d", dbPort);
            // dbName 数据库名可以为空，比如oracle是不需要数据库名的
            dbName = db.getString(ConfigDef.DB.DB_NAME);
            logger.debug("assignment dbName ==> %s", dbName);

            if (StringUtil.isEmpty(db.getString(ConfigDef.DB.USER))) {
                logger.error("can not resolve the required field: db.user");
                return false;
            }
            dbUser = db.getString(ConfigDef.DB.USER);
            logger.debug("assignment dbUser ==> %s", dbUser);

            if (StringUtil.isEmpty(db.getString(ConfigDef.DB.PASSWORD))) {
                logger.error("can not resolve the required field: db.password");
                return false;
            }
            dbPwd = db.getString(ConfigDef.DB.PASSWORD);
            logger.debug("assignment dbPwd ==> %s", dbPwd);

            if (StringUtil.isEmpty(db.getString(ConfigDef.DB.DATASOURCE))) {
                logger.error("can not resolve the required field: db.dataSource");
                return false;
            }
            dbDataSource = db.getString(ConfigDef.DB.DATASOURCE);
            logger.debug("assignment dbDataSource ==> %s", dbDataSource);
        }
        // ---- end of assignment db

        // ---- start assignment cache
        // cache 字段为可选
        JSONObject cache = obj.getJSONObject(ConfigDef.Cache.TAG);
        if (cache != null) {
            hasCache = true;
            if (StringUtil.isEmpty(cache.getString(ConfigDef.Cache.HOST))) {
                logger.error("can not resolve the required field: cache.host");
                return false;
            }
            cacheHost = cache.getString(ConfigDef.Cache.HOST);
            logger.debug("assignment cacheHost ==> %s", cacheHost);

            if (cache.getIntValue(ConfigDef.Cache.PORT) <= 0) {
                logger.error("can not resolve the required field: cache.port");
                return false;
            }
            cachePort = cache.getIntValue(ConfigDef.Cache.PORT);
            logger.debug("assignment cacheHost ==> %d", cachePort);
        }

        return true;
    }

    // DB Cache都是可选字段，因此，可以通过hasXX得到是否有该字段的信息
    public boolean hasDB() {
        return hasDB;
    }

    public boolean hasCache() {
        return hasCache;
    }

    // getter
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
