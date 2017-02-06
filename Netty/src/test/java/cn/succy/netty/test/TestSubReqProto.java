package cn.succy.netty.test;

import cn.succy.netty.bean.SubscribeReq;
import cn.succy.netty.protobuf.SubscribeReqProto;
import com.google.protobuf.InvalidProtocolBufferException;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 测试SubReqProto的编解码
 *
 * @author Succy
 * @date 2017-02-06 22:30
 **/

public class TestSubReqProto {
    /**
     * 对pojo进行编码
     * @param req 要进行编码的pojo对象
     * @return 返回编码后的字节数组
     */
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    /**
     * 对字节数组进行解码成对于的pojo对象
     * @param data 需要进行解码的数据
     * @return 返回解码成的pojo对象
     * @throws InvalidProtocolBufferException
     */
    private static SubscribeReqProto.SubscribeReq decode(byte[] data) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(data);
    }

    /**
     * protobuf构造pojo对象是需要先通过builder来构造的，其getter/setter方法也都是builder的方法
     * 构造好builder对象之后，可以通过builder对象的build方法返回得到对应的pojo对象
     * @return 返回构造好的pojo对象
     */
    private static SubscribeReqProto.SubscribeReq createSubReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setPhoneNum("13078077563");
        builder.setProductName("Netty in action");
        builder.setUserName("Succy");
        builder.addAllAddress(Arrays.asList("广州白云区"));

        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq subReq = createSubReq();
        //System.out.println("before encode: " + subReq.toString() );
        // encode
        byte[] encode = encode(subReq);// 56
        System.out.println("encode length: " + encode.length);
        // decode
        SubscribeReqProto.SubscribeReq decode = decode(encode);
        //System.out.println("after decode: " + decode);
        //System.out.println("compare native req and decode req --> " + decode.equals(subReq));

        byteBufferTest();// 66
        // 实践证明使用protobuf编码比使用ByteBuffer直接编码成二进制所占用的空间还要少
    }

    /**
     * 使用ByteBuffer对pojo进行编码成二进制，测试其编码后的二进制数组大小
     */
    private static void byteBufferTest() {
        SubscribeReq subReq = new SubscribeReq();
        subReq.setSubReqID(1);
        subReq.setPhoneNum("13078077563");
        subReq.setProductName("Netty in action");
        subReq.setUserName("Succy");
        subReq.setAddress("广州白云区");

        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.putInt(subReq.getSubReqID());
        buf.put(subReq.getAddress().getBytes());
        buf.put(subReq.getPhoneNum().getBytes());
        buf.put(subReq.getProductName().getBytes());
        buf.put(subReq.getUserName().getBytes());
        buf.putInt(subReq.getAddress().getBytes().length);
        buf.putInt(subReq.getPhoneNum().getBytes().length);
        buf.putInt(subReq.getProductName().getBytes().length);
        buf.putInt(subReq.getUserName().getBytes().length);

        buf.flip();
        byte[] result = new byte[buf.remaining()];
        buf.get(result);

        System.out.println("byteBuffer encode length : " + result.length);

    }
}
