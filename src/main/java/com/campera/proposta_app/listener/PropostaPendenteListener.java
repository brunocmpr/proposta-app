package com.campera.proposta_app.listener;

import com.campera.proposta_app.dto.PropostaResponseDto;
import com.campera.proposta_app.entity.Proposta;
import com.campera.proposta_app.mapper.PropostaMapper;
import com.campera.proposta_app.repository.PropostaRepository;
import com.campera.proposta_app.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PropostaPendenteListener {

    private PropostaRepository propostaRepository;
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaEmAnalise(Proposta proposta) {
        atualizarProposta(proposta);
        PropostaResponseDto propostaResponseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        webSocketService.notificar(propostaResponseDto);
    }

    private void atualizarProposta(Proposta proposta) {
        propostaRepository.atualizarProposta(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());
    }
}
