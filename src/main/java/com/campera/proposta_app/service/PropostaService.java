package com.campera.proposta_app.service;

import com.campera.proposta_app.config.RabbitMQConfiguration;
import com.campera.proposta_app.dto.PropostaRequestDto;
import com.campera.proposta_app.dto.PropostaResponseDto;
import com.campera.proposta_app.entity.Proposta;
import com.campera.proposta_app.repository.PropostaRepository;
import com.campera.proposta_app.mapper.PropostaMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final NotificacaoService notificacaoService;


    private String exchange;

    public PropostaService(PropostaRepository propostaRepository
            , NotificacaoService notificacaoService
            , @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoService = notificacaoService;
        this.exchange = exchange;
    }

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);
        propostaRepository.save(proposta);

        PropostaResponseDto response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        notificacaoService.notificar(response, this.exchange);
        return response;
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}