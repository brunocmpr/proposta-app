package com.campera.proposta_app.service;

import com.campera.proposta_app.dto.PropostaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoService {
    private final RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponseDto proposta, String exchange){
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }
}
