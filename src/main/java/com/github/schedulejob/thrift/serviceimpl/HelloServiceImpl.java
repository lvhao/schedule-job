package com.github.schedulejob.thrift.serviceimpl;

import com.github.schedulejob.thrift.protocol.TReq;
import com.github.schedulejob.thrift.protocol.TResp;
import com.google.common.base.Strings;
import com.github.schedulejob.thrift.protocol.HelloService;
import org.apache.thrift.TException;

import java.text.MessageFormat;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * HelloService 实际实现类
 *
 * @author: lvhao
 * @since: 2016-7-8 18:13
 */
public class HelloServiceImpl implements HelloService.Iface{

    @Override
    public TResp sayHi(TReq tReq) throws TException {
        checkArgument(!Strings.isNullOrEmpty(tReq.getName()),"name不能为空");
        checkArgument(tReq.getSeq() > 0,"seq必须大于0");
        TResp tResp = new TResp();
        tResp.setCode(0);
        tResp.setMsg("成功");
        tResp.setData(MessageFormat.format(
                "{1}呼叫,序号->{0}",
                String.valueOf(tReq.getSeq()),
                tReq.getName())
        );
        return tResp;
    }
}
