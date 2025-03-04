package com.campera.proposta_app.listener;

import com.campera.proposta_app.entity.Proposta;
import com.campera.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PropostaPendenteListener {

    private PropostaRepository propostaRepository;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaEmAnalise(Proposta proposta) {
        propostaRepository.save(proposta);
    }
}
