package com.campera.proposta_app.service;

import com.campera.proposta_app.dto.PropostaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketService {

    private SimpMessagingTemplate template;

    public void notificar(PropostaResponseDto proposta){
        template.convertAndSend("/propostas", proposta);
    }
}
