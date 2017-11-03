package com.github.schedulejob.service;


import com.github.schedulejob.anno.TargetDataSource;
import com.github.schedulejob.common.AppConst;
import com.github.schedulejob.domain.TicketDO;
import com.github.schedulejob.mapper.TicketMapper;
import com.github.schedulejob.po.TicketPO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Ticket 服务类
 *
 * @author: lvhao
 * @since: 2016-4-12 12:12
 */
@Service
@Transactional
@TargetDataSource(AppConst.DbKey.READ)
public class TicketService implements DefaultDataSourceService {

    @Autowired
    private TicketMapper ticketMapper;

    @Transactional(readOnly = true)
    @TargetDataSource(AppConst.DbKey.READ)
    public int queryCount(int id){
        return ticketMapper.queryCount(id);
    }

    public List<TicketDO> queryList(){
        List<TicketPO> ticketPOList = ticketMapper.queryList();
        return ticketPOList.stream().map(po -> {
            TicketDO ticketDO = new TicketDO();
            BeanUtils.copyProperties(po, ticketDO);
            return ticketDO;
        }).collect(Collectors.toList());
    }

    @TargetDataSource(AppConst.DbKey.WRITE)
    public void updateStock(int id){
        ticketMapper.updateStock(id);
    }

    @TargetDataSource(AppConst.DbKey.WRITE)
    public String buyNow(int id){
        int cnt = this.queryCount(id);
        if(cnt > 0){
            this.updateStock(id);
            return "ok";
        }
        return "fail";
    }
}
