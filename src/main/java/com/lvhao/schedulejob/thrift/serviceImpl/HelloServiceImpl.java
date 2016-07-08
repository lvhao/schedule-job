package com.lvhao.schedulejob.thrift.serviceimpl;

import com.google.common.base.Strings;
import com.lvhao.schedulejob.thrift.protocol.HelloService;
import com.lvhao.schedulejob.thrift.protocol.TReq;
import com.lvhao.schedulejob.thrift.protocol.TResp;
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
                "第{0}次,发送者{1}",
                String.valueOf(tReq.getSeq()),
                tReq.getName())
        );
        return tResp;
    }
}
