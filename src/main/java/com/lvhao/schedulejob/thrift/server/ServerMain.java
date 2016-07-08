package com.lvhao.schedulejob.thrift.server;

import com.lvhao.schedulejob.thrift.protocol.HelloService;
import com.lvhao.schedulejob.thrift.serviceimpl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

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
            // 设置协议工厂为 TBinaryProtocol.Factory
            TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();
            // 关联处理器与 HelloService 服务的实现
            TProcessor processor = new HelloService.Processor(new HelloServiceImpl());

            // TODO
            TServer server = new TThreadPoolServer(null);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
