package cn.succy.rpc.comm.util;

/**
 * 对字符串进行处理的工具类
 * @author Succy
 * @date 2017/2/13 9:56
 */
public class StringUtil {

    /**
     * 私有构造器，防止被实例化
     */
    private StringUtil() {

    }

    /**
     * 判断字符串是否为空
     * @param str 待判断的字符串
     * @return 如果待判定的字符串为空字符串或者为null，返回true,反之返回false
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str))
            return true;
        return false;
    }
}
