package com.github.schedulejob.thrift.client;

import com.github.schedulejob.thrift.protocol.TReq;
import com.github.schedulejob.thrift.protocol.HelloService;
import com.github.schedulejob.thrift.protocol.TResp;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * Thrift客户端
 *
 * @author: lvhao
 * @since: 2016-7-11 16:31
 */
public class ClientMain {
    private static final int PORT = 7777;

    public static void main(String[] args) {
        TFramedTransport tFramedTransport = new TFramedTransport(new TSocket("localhost",PORT));
        TCompactProtocol protocol = new TCompactProtocol(tFramedTransport);
        TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(protocol,"HelloService");
        HelloService.Iface helloService = new HelloService.Client(tMultiplexedProtocol);

        TResp tResp = new TResp();
        TReq tReq = new TReq();
        tReq.setSeq(1);
        tReq.setName("jack");
        try {
            tFramedTransport.open();
            tResp = helloService.sayHi(tReq);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            tFramedTransport.close();
        }
        System.out.println("tResp = " + tResp);
    }
}
