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

	static final String queueName = "bookingQueue";
	static final String queueName2 = "debookingQueue";

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	Queue queue2() {
		return new Queue(queueName2, false);
	}

	@Bean
	Binding binding1(TopicExchange exchange) {
		return BindingBuilder.bind(queue()).to(exchange).with("foo.bar.#");
	}

	@Bean
	Binding binding2(TopicExchange exchange) {
		return BindingBuilder.bind(queue2()).to(exchange).with("foo2.bar.#");
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringRestPostgreSqlApplication.class, args);
	}
}
