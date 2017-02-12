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
        System.out.println("init is " + (init ? "success" : "fail"));

        System.out.println(config.toString());
    }
}
