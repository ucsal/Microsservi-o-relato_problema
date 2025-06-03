package com.exemplo.relatoproblema.micro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.relatoproblema.micro.dto.RelatoProblemaDTO;
import com.exemplo.relatoproblema.micro.model.service.RelatoProblemaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/relatos")
@RequiredArgsConstructor
public class RelatoProblemaController {

    private final RelatoProblemaService relatoService;

    // Apenas Administradores podem visualizar todos os relatos
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RelatoProblemaDTO>> getAll() {
        return ResponseEntity.ok(relatoService.getAll());
    }

    // Busca relatos por solicitação específica - restrito a Administradores
    @GetMapping("/solicitacao/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RelatoProblemaDTO>> getBySolicitacao(@PathVariable Long id) {
        return ResponseEntity.ok(relatoService.getBySolicitacao(id));
    }

    // Criação de relatos restrita a Professores
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<RelatoProblemaDTO> create(@RequestBody RelatoProblemaDTO relatoDTO) {
        return ResponseEntity.status(201).body(relatoService.create(relatoDTO));
    }
}