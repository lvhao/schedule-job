package com.github.schedulejob.controller;

import com.github.schedulejob.service.TicketService;
import com.github.schedulejob.util.ResponseBuilder;
import com.github.schedulejob.common.Response;
import com.github.schedulejob.domain.TicketDO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ticket 路由控制
 *
 * @author: lvhao
 * @since: 2016-4-12 12:12
 */
@Api(tags = {"ticket"}, value = "ticket 相关操作")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{id}/count")
    public Integer queryCount(@PathVariable int id){
        return ticketService.queryCount(id);
    }

    @GetMapping("/list")
    public List<TicketDO> queryList(){
        return ticketService.queryList();
    }

    @PostMapping("/buy_now/{id}")
    public String buyNow(@PathVariable int id){
        return ticketService.buyNow(id);
    }

    @PostMapping("/update")
    public Response updateTicket(@RequestBody TicketDO ticketDO){
        ticketService.updateStock(ticketDO.getId());
        return ResponseBuilder.newResponse().build();
    }
}
