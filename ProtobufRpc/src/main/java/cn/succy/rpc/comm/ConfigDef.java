package cn.succy.rpc.comm;

/**
 * Config参数配置定义类
 *
 * @author Succy
 * @date 2017-02-12 17:31
 **/

public class ConfigDef {
    public static final class Server {
        public static final String TAG = "server";
        public static final String HOST = "host";
        public static final String PORT = "port";
        public static final String SERVER_NAME = "serverName";
    }
    public static final class Client {
        public static final String TAG = "client";
        public static final String HOST = "host";
        public static final String PORT = "port";
    }

    public static final class DB{
        public static final String TAG = "db";
        public static final String URL = "url";
        public static final String USER = "user";
        public static final String DB_NAME = "dbName";
        public static final String PORT = "port";
        public static final String PASSWORD = "password";
        public static final String DATASOURCE = "dataSource";
    }

    public static final class Cache {
        public static final String TAG = "cache";
        public static final String HOST = "host";
        public static final String PORT = "port";
    }
}
