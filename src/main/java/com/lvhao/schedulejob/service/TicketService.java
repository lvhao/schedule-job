package com.lvhao.schedulejob.service;


import com.lvhao.schedulejob.anno.TargetDataSource;
import com.lvhao.schedulejob.common.AppConst;
import com.lvhao.schedulejob.domain.TicketDomain;
import com.lvhao.schedulejob.mapper.TicketMapper;
import com.lvhao.schedulejob.po.TicketPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Ticket 服务类
 *
 * @author: lvhao
 * @since: 2016-4-12 12:12
 */
@Service
public class TicketService extends BaseService{

    @Autowired
    private TicketMapper ticketMapper;

    @TargetDataSource(AppConst.DBType.READ)
    public int queryCount(int id){
        return ticketMapper.queryCount(id);
    }

    public List<TicketDomain> queryList(){
        List<TicketPO> ticketPOList = ticketMapper.queryList();
        return ticketPOList.stream().map(po -> {
            TicketDomain ticketDomain = new TicketDomain();
            BeanUtils.copyProperties(po,ticketDomain);
            return ticketDomain;
        }).collect(Collectors.toList());
    }

    @TargetDataSource(AppConst.DBType.WRITE)
    public void updateStock(int id){
        ticketMapper.updateStock(id);
    }

    @TargetDataSource(AppConst.DBType.WRITE)
    public String buyNow(int id){
        int cnt = this.queryCount(id);
        if(cnt > 0){
            this.updateStock(id);
            return "ok";
        }
        return "fail";
    }
}
