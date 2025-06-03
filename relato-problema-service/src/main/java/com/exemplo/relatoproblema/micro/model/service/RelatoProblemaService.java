package com.exemplo.relatoproblema.micro.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.exemplo.relatoproblema.micro.dto.RelatoProblemaDTO;
import com.exemplo.relatoproblema.micro.model.entity.RelatoProblema;
import com.exemplo.relatoproblema.micro.model.repository.RelatoProblemaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Serviço responsável pela lógica de negócio relacionada a relatos de problemas.
 * Realiza validações, conversões DTO/entidade e integração com serviços externos.
 */
@Service
@RequiredArgsConstructor
public class RelatoProblemaService {

    private final RelatoProblemaRepository relatoRepository;
    private final WebClient webClient;

    @Value("${solicitacao.service.url}")
    private String solicitacaoServiceUrl;

    /**
     * Recupera todos os relatos de problemas convertidos para DTO.
     * @return Lista de RelatoProblemaDTO ordenados por data (sugestão)
     */
    public List<RelatoProblemaDTO> getAll() {
        return relatoRepository.findAllByOrderByDataRelatoDesc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca relatos por ID de solicitação com validação de existência.
     * @param solicitacaoId ID da solicitação
     * @return Lista de DTOs dos relatos encontrados
     * @throws ResponseStatusException 404 se a solicitação não existir
     */
    public List<RelatoProblemaDTO> getBySolicitacao(Long solicitacaoId) {
        validateSolicitacaoExists(solicitacaoId);
        return relatoRepository.findBySolicitacaoId(solicitacaoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo relato de problema com validações.
     * @param relatoDTO DTO com dados do relato
     * @return DTO do relato criado
     * @throws ResponseStatusException 400 para dados inválidos
     * @throws ResponseStatusException 404 se solicitação não existir
     */
    public RelatoProblemaDTO create(RelatoProblemaDTO relatoDTO) {
        validateSolicitacaoExists(relatoDTO.getSolicitacaoId());
        
        if (relatoDTO.getDataRelato() == null) {
            relatoDTO.setDataRelato(LocalDate.now());
        } else if (relatoDTO.getDataRelato().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Data do relato não pode ser futura"
            );
        }

        RelatoProblema relato = convertToEntity(relatoDTO);
        return convertToDTO(relatoRepository.save(relato));
    }

    /**
     * Valida existência da solicitação no serviço externo.
     * @throws ResponseStatusException 404 se não encontrada
     */
    private void validateSolicitacaoExists(Long solicitacaoId) {
        try {
            Boolean exists = webClient.get()
                .uri(solicitacaoServiceUrl + "/{id}/exists", solicitacaoId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
            
            if (exists == null || !exists) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Solicitação não encontrada"
                );
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Erro ao verificar solicitação"
            );
        }
    }

    /**
     * Converte entidade para DTO.
     */
    private RelatoProblemaDTO convertToDTO(RelatoProblema relato) {
        return new RelatoProblemaDTO(
            relato.getId(),
            relato.getSolicitacaoId(),
            relato.getDescricao(),
            relato.getDataRelato()
        );
    }

    /**
     * Converte DTO para entidade.
     */
    private RelatoProblema convertToEntity(RelatoProblemaDTO dto) {
        return RelatoProblema.builder()
            .solicitacaoId(dto.getSolicitacaoId())
            .descricao(dto.getDescricao())
            .dataRelato(dto.getDataRelato())
            .build();
    }
}