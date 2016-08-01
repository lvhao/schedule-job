package com.github.schedulejob.mapper;

import com.github.schedulejob.po.TicketPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ticket Mapper
 *
 * @author: lvhao
 * @since: 2016-4-12 12:07
 */
@Mapper
public interface TicketMapper {
    int queryCount(@Param("id") int id);
    List<TicketPO> queryList();
    void updateStock(@Param("id") int id);
}
