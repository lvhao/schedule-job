package com.github.schedulejob.controller;

import com.github.schedulejob.domain.TicketDomain;
import com.github.schedulejob.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/{id}/count",method = RequestMethod.GET)
    public Integer queryCount(@PathVariable int id){
        return ticketService.queryCount(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<TicketDomain> queryList(){
        return ticketService.queryList();
    }

    @RequestMapping(value = "/buy_now/{id}",method = RequestMethod.POST)
    public String buyNow(@PathVariable int id){
        return ticketService.buyNow(id);
    }
}
