package com.javasampleapproach.springrest.postgresql.controller;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class RabbitmqConfiguration {

    static final String topicExchangeName = "spring-boot-exchange";

    static final String queueName = "spring-boot";

    //gestisce la coda
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    //controlla lo scambio messaggi
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    //permette il binding tra la coda e l'exchange, i messaggi che arrivano al topic exchange vengono inseriti automaticamente nella coda
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    /*
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/

}
