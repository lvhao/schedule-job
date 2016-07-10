package com.lvhao.schedulejob.service;

import com.lvhao.schedulejob.thrift.protocol.HelloService;
import com.lvhao.schedulejob.thrift.protocol.TReq;
import com.lvhao.schedulejob.thrift.protocol.TResp;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 测试调用Thrift服务
 * Created by root on 2016/7/10 0010.
 */
public class InvokeThriftService extends BaseService {
    private static final int PORT = 7777;

    public static void main(String[] args) {
        TTransport tTransport = new TSocket("localhost",PORT);
        TCompactProtocol protocol = new TCompactProtocol(tTransport);
        TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(protocol,"HelloService");
        HelloService.Client client = new HelloService.Client(tMultiplexedProtocol);

        TResp tResp = new TResp();
        TReq tReq = new TReq();
        tReq.setSeq(1);
        tReq.setName("jack");
        try {
            tTransport.open();
            tResp = client.sayHi(tReq);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            tTransport.close();
        }
        System.out.println("tResp = " + tResp);
    }
}
