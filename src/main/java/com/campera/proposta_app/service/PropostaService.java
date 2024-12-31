package com.campera.proposta_app.service;

import com.campera.proposta_app.dto.PropostaRequestDto;
import com.campera.proposta_app.dto.PropostaResponseDto;
import com.campera.proposta_app.entity.Proposta;
import com.campera.proposta_app.repository.PropostaRepository;
import com.campera.proposta_app.mapper.PropostaMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);
        propostaRepository.save(proposta);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}