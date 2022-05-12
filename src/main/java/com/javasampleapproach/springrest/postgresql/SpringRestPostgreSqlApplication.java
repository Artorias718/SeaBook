package com.javasampleapproach.springrest.postgresql;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@EnableDiscoveryClient
@SpringBootApplication
public class SpringRestPostgreSqlApplication {
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

	public static void main(String[] args) {
		SpringApplication.run(SpringRestPostgreSqlApplication.class, args);
	}
}
