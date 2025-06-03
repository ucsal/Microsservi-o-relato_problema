package com.exemplo.relatoproblema.micro.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade JPA que representa um relato de problema no banco de dados.
 * Mapeia para a tabela 'relato_problema' (se aplicável) e contém
 * os campos essenciais para registro de problemas no sistema.
 */
@Entity
@Table(name = "relato_problema")
@Getter
@Setter
@Builder                    // ← ADICIONE ESTA ANOTAÇÃO
@NoArgsConstructor         // ← ADICIONE ESTA ANOTAÇÃO  
@AllArgsConstructor        // ← ADICIONE ESTA ANOTAÇÃO
public class RelatoProblema {
    
    /**
     * ID único do relato no banco de dados.
     * Gerado automaticamente pelo banco com estratégia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID da solicitação relacionada.
     * Representa um relacionamento lógico (não gerenciado pelo JPA).
     * Considerar transformar em @ManyToOne se precisar de navegabilidade.
     */
    @Column(name = "solicitacao_id", nullable = false)
    private Long solicitacaoId;

    /**
     * Descrição detalhada do problema.
     * Deveria conter informações suficientes para diagnóstico.
     */
    @Column(nullable = false, length = 2000)
    private String descricao;

    /**
     * Data do registro do problema.
     * Preenchida automaticamente no momento da criação.
     */
    @Column(name = "data_relato", nullable = false)
    private LocalDate dataRelato;

    // REMOVA ESTE MÉTODO - o Lombok vai gerar automaticamente
    // public static Object builder() {
    //     throw new UnsupportedOperationException("Unimplemented method 'builder'");
    // }
}