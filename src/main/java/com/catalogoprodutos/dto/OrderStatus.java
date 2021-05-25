package com.catalogoprodutos.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderStatus {

   
    private Order order;
    private String status;
    private String message;

    public OrderStatus(Order order, String status, String message) {
        this.order = order;
        this.status = status;
        this.message = message;
    }
}
