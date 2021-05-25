package com.catalogoprodutos.consumer;

import com.catalogoprodutos.config.MessagingConfig;
import com.catalogoprodutos.dto.OrderStatus;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumerMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message recieved from queue : " + orderStatus);

    }

}
