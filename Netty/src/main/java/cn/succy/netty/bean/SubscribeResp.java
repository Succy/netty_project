package cn.succy.netty.bean;

import java.io.Serializable;

/**
 * 订购请求响应的pojo类
 *
 * @author Succy
 * @date 2017-02-05 12:06
 **/

public class SubscribeResp implements Serializable {
    private static final long serialVersionUID = -756716393234730032L;
    private int subReqID;// 订单编号
    private int respCode;// 响应码，0代表成功
    private String desc;// 响应描述

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "subReqID=" + subReqID +
                ", respCode=" + respCode +
                ", desc='" + desc + '\'' +
                '}';
    }
}
