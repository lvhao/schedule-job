package com.lvhao.schedulejob.mapper;

import com.lvhao.schedulejob.po.TicketPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ticket Mapper
 *
 * @author: lvhao
 * @since: 2016-4-12 12:07
 */
@Repository
public interface TicketMapper {
    int queryCount(@Param("id") int id);
    List<TicketPO> queryList();
    void updateStock(@Param("id") int id);
}
