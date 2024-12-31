package com.campera.proposta_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String QUEUE_PENDENTE_ANALISE_CREDITO = "proposta-pendente.ms-analise-credito";
    public static final String QUEUE_PENDENTE_NOTIFICACAO = "proposta-pendente.ms-notificacao";
    public static final String QUEUE_CONCLUIDA_NOTIFICACAO = "proposta-concluida.ms-notificacao";
    public static final String QUEUE_CONCLUIDA_PROPOSTA = "proposta-concluida.ms-proposta";
    public static final String EXCHANGE_PENDENTE = "proposta-pendente.ex";

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
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return QueueBuilder.durable(QUEUE_CONCLUIDA_PROPOSTA).build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
        return QueueBuilder.durable(QUEUE_CONCLUIDA_NOTIFICACAO).build();
    }

    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Initializes the RabbitMQ and creates the queues and exchanges
     * @param rabbitAdmin Bean that initializes the RabbitMQ
     * @return ApplicationListener<ApplicationReadyEvent> that initializes the RabbitMQ
     */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente(){
        return ExchangeBuilder.fanoutExchange(EXCHANGE_PENDENTE).build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsAnaliseCredito(){
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao(){
        return BindingBuilder
                .bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }
}
