package com.exemplo.relatoproblema.micro.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.relatoproblema.micro.model.entity.RelatoProblema;

/**
 * Repositório JPA para operações de persistência relacionadas a RelatoProblema.
 * Herda operações CRUD básicas do JpaRepository e define consultas customizadas.
 */
@Repository
public interface RelatoProblemaRepository extends JpaRepository<RelatoProblema, Long> {

    /**
     * Busca relatos de problemas pelo ID da solicitação associada.
     * Implementação gerada automaticamente pelo Spring Data JPA.
     * 
     * @param solicitacaoId ID da solicitação relacionada
     * @return Lista de relatos associados à solicitação (pode ser vazia)
     */
    List<RelatoProblema> findBySolicitacaoId(Long solicitacaoId);

    /**
     * Busca todos os relatos ordenados por data decrescente.
     * Implementação gerada automaticamente pelo Spring Data JPA.
     * 
     * @return Lista de relatos ordenados por data (mais recentes primeiro)
     */
    List<RelatoProblema> findAllByOrderByDataRelatoDesc();  // ← CORRIGIDO!

    // Sugestão de métodos adicionais:
    /*
    @Query("SELECT r FROM RelatoProblema r WHERE r.dataRelato BETWEEN :startDate AND :endDate")
    List<RelatoProblema> findBetweenDates(@Param("startDate") LocalDate start, 
                                         @Param("endDate") LocalDate end);

    List<RelatoProblema> findByDescricaoContainingIgnoreCase(String termo);
    */
}