package com.github.schedulejob.po;

/**
 * ticket 实体类
 *
 * @author: lvhao
 * @since: 2016-4-12 12:06
 */
public class TicketPO {
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
        final StringBuffer sb = new StringBuffer("TicketPO{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", quantityOfStocks=").append(quantityOfStocks);
        sb.append('}');
        return sb.toString();
    }
}
