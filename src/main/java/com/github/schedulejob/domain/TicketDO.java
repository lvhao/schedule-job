package com.github.schedulejob.domain;

/**
 * ticket 业务类
 *
 * @author: lvhao
 * @since: 2016-4-12 12:06
 */
public class TicketDO {
    private int id;
    private String name;
    private int quantityOfStocks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityOfStocks() {
        return quantityOfStocks;
    }

    public void setQuantityOfStocks(int quantityOfStocks) {
        this.quantityOfStocks = quantityOfStocks;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TicketDO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", quantityOfStocks=").append(quantityOfStocks);
        sb.append('}');
        return sb.toString();
    }
}
