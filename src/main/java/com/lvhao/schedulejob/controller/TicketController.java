package com.lvhao.schedulejob.controller;

import com.lvhao.schedulejob.common.Response;
import com.lvhao.schedulejob.domain.TicketDo;
import com.lvhao.schedulejob.service.TicketService;
import com.lvhao.schedulejob.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ticket 路由控制
 *
 * @author: lvhao
 * @since: 2016-4-12 12:12
 */
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
    public List<TicketDo> queryList(){
        return ticketService.queryList();
    }

    @PostMapping("/buy_now/{id}")
    public String buyNow(@PathVariable int id){
        return ticketService.buyNow(id);
    }

    @PostMapping("/update")
    public Response updateTicket(@RequestBody TicketDo ticketDo){
        ticketService.updateStock(ticketDo.getId());
        return ResponseBuilder.newResponse().build();
    }
}
