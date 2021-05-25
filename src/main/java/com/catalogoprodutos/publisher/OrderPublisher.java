package com.catalogoprodutos.publisher;

import java.util.UUID;

import com.catalogoprodutos.config.MessagingConfig;
import com.catalogoprodutos.dto.Order;
import com.catalogoprodutos.dto.OrderStatus;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderPublisher {
    
    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{marketName}")
    public String bookOrder (@RequestBody Order order,@PathVariable String marketName){
        order.setOrderId(UUID.randomUUID().toString());
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed succesfully in "+marketName);
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
        return "Succes !!";
    }
}

