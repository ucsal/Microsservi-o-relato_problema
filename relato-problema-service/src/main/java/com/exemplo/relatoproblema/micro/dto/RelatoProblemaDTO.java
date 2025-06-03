package com.exemplo.relatoproblema.micro.dto;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatoProblemaDTO {
    private Long id;
    private Long solicitacaoId;  // ID da solicitação associada
    private String descricao;    // Descrição do problema
    private LocalDate dataRelato; // Data do relato
}
