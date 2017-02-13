package cn.succy.rpc.comm;

/**
 * 测试Configuration类的获取情况
 *
 * @author Succy
 * @date 2017-02-12 19:19
 **/

public class TestConfig {
    public static void main(String[] args) {
        ServerConfig config = new ServerConfig("ProtobufRpc/src/config/TestSvr.json");
        boolean init = config.init();
        // System.out.println("init is " + (init ? "success" : "fail"));
        //
        // System.out.println(config.toString());
        if (init) {
            System.out.println("hasDb ==> " + (config.hasDB() ? "true" : "false"));
            System.out.println("hasCache ==> " + (config.hasCache() ? "true" : "false"));
            System.out.println(config.getServerHost());
        }

        String configFile = "ProtobufRpc/src/config/TestClient.json";
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setConfigFile(configFile);
        boolean cliInit = clientConfig.init();
        if (cliInit) {
            System.out.println(clientConfig.toString());
        }
    }
}
