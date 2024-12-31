package com.campera.proposta_app.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String QUEUE_PENDENTE_ANALISE_CREDITO = "proposta-pendente-ms.analise-credito";
    public static final String QUEUE_PENDENTE_NOTIFICACAO = "proposta-pendente-ms.notificacao";
    public static final String QUEUE_CONCLUIDA_NOTIFICACAO = "proposta-concluida-ms.proposta";
    public static final String QUEUE_CONCLUIDA_PROPOSTA = "proposta-concluida-ms.notificacao";

    private final ConnectionFactory connectionFactory;

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        return QueueBuilder.durable(QUEUE_PENDENTE_ANALISE_CREDITO).build();
    }
    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao() {
        return QueueBuilder.durable(QUEUE_PENDENTE_NOTIFICACAO).build();
    }
    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
        return QueueBuilder.durable(QUEUE_CONCLUIDA_NOTIFICACAO).build();
    }
    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return QueueBuilder.durable(QUEUE_CONCLUIDA_PROPOSTA).build();
    }

    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
