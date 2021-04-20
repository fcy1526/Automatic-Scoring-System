package com.fcy.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String queueName = "automatic_scoring_system";

    @Bean
    Queue queue() {
        return new Queue(queueName ,false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("spring-cloud-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(queueName);
    }
}
