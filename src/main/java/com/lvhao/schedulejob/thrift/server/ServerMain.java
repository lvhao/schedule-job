package com.lvhao.schedulejob.thrift.server;

import com.lvhao.schedulejob.thrift.protocol.HelloService;
import com.lvhao.schedulejob.thrift.serviceimpl.HelloServiceImpl;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.text.MessageFormat;

/**
 * Thrift启动类
 *
 * @author: lvhao
 * @since: 2016-7-8 18:09
 */
public class ServerMain {
    private static final int PORT = 7777;
    public static void main(String[] args) {
        try {
            // 设置服务端口为 7777
            TServerSocket serverTransport = new TServerSocket(PORT);

            // 设置协议工厂为 TCompactProtocol.Factory
            TCompactProtocol.Factory proFactory = new TCompactProtocol.Factory();

            // 关联处理器与 HelloService 服务的实现
            TMultiplexedProcessor tMultiplexedProcessor = new TMultiplexedProcessor();
            tMultiplexedProcessor.registerProcessor("HelloService",new HelloService.Processor(new HelloServiceImpl()));

            TThreadPoolServer.Args tArgs =new TThreadPoolServer.Args(serverTransport);
            tArgs.protocolFactory(proFactory);
            tArgs.processor(tMultiplexedProcessor);
            TThreadPoolServer server = new TThreadPoolServer(tArgs);
            System.out.println(MessageFormat.format("server start listen on {0}...",String.valueOf(PORT)));
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
