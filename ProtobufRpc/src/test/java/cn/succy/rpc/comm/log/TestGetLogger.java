package cn.succy.rpc.comm.log;

/**
 * 测试logger类
 *
 * @author Succy
 * @date 2017-02-12 13:24
 **/

public class TestGetLogger {

    public static void main(String[] args) {
        //LoggerFactory.configure("H:\\Netty\\netty_project\\ProtobufRpc\\file\\log4j.properties");
        Logger logger = LoggerFactory.getLogger(TestGetLogger.class);

        logger.debug("这是debug信息", new Exception("我是Exception异常"));
        logger.debug("这是debug信息,带参数的哦！%s-->%d", "参数1", 2);
        logger.info("这是info信息", new Exception("我是Exception异常"));
        logger.info("这是info信息,我是有参数的哦%s, %d, %f", "参数", 23, 0.5);
        logger.error("这是error信息");
        logger.warn("这是warn信息");

        //LoggerJdkAdapter adapter = new LoggerJdkAdapter(TestGetLogger.class);
        //adapter.debug("this is a debug msg");
    }

}
