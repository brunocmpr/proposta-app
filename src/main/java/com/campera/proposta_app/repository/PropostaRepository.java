package com.campera.proposta_app.repository;

import com.campera.proposta_app.entity.Proposta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {
    List<Proposta> findAllByIntegradaIsFalse();
}