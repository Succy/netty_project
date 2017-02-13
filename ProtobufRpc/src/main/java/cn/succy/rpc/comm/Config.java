package cn.succy.rpc.comm;

import cn.succy.rpc.comm.log.Logger;
import cn.succy.rpc.comm.log.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 配置各种参数的配置工具类
 *
 * @author Succy
 * @date 2017-02-12 17:23
 **/

public abstract class Config {
    private String configFile;
    private JSONObject jsonObject;
    protected static Logger logger = LoggerFactory.getLogger(Config.class);

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    /**
     * 对配置文件进行初始化，如果用户在构造Config类的时候就已经指定了
     * 配置文件，则可以直接构造完Config类之后直接调用此方法，若构造时
     * 使用的是空构造方法，则必须先设置configFile才可以调用该方法进行
     * 初始化
     *
     * @return 初始化成功返回true，反之返回false
     */
    public boolean init() {
        if (this.configFile == null || "".equals(this.configFile)) {
            logger.error("The Configure file not found, Please set configure file first!");
            return false;
        }
        BufferedReader bufReader = null;
        StringBuilder jsonTextBuilder = new StringBuilder(1024);
        try {
            bufReader = new BufferedReader(new FileReader(configFile));
            String line;
            while ((line = bufReader.readLine()) != null) {
                jsonTextBuilder.append(line);
            }
            jsonObject = JSONObject.parseObject(jsonTextBuilder.toString());
            // 解析参数
            if (!assignParam(jsonObject))
                return false;

        } catch (IOException e) {
            logger.error("read config file error", e);
            return false;
        } finally {
            try {
                if (bufReader != null)
                    bufReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 取出jsonObj的值，对成员变量进行赋值,在本抽象类中该方法不做处理
     * 留给子类根据需求做处理，父类的Init()方法通过多态调用
     *
     * @param obj 封装好信息的jsonObj
     */
    protected abstract boolean assignParam(JSONObject obj);
}
