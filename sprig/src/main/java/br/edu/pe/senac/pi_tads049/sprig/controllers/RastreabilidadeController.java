package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.dto.RastreamentoDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.RastreamentoService;

@RestController
@RequestMapping("/rastreabilidade")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO', 'AGRICULTOR')")
public class RastreabilidadeController {

    @Autowired
    private RastreamentoService rastreamentoService;

    /**
     * Rastreia entrega por ID
     * Acesso: GESTOR, TECNICO, AGRICULTOR
     */
    @GetMapping("/{idEntrega}")
    public ResponseEntity<RastreamentoDTO> rastrearEntrega(@PathVariable Integer idEntrega) {
        RastreamentoDTO rastreamento = rastreamentoService.rastrear(idEntrega);
        return ResponseEntity.ok(rastreamento);
    }

    /**
     * Rastreia entrega por código de rastreio
     * Acesso: GESTOR, TECNICO, AGRICULTOR
     */
    @GetMapping("/codigo/{codigoRastreio}")
    public ResponseEntity<RastreamentoDTO> rastrearPorCodigo(@PathVariable String codigoRastreio) {
        RastreamentoDTO rastreamento = rastreamentoService.rastrearPorCodigo(codigoRastreio);
        return ResponseEntity.ok(rastreamento);
    }

    /**
     * Atualiza localização atual da entrega
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{idEntrega}/localizacao")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> atualizarLocalizacao(
            @PathVariable Integer idEntrega,
            @RequestParam BigDecimal latitude,
            @RequestParam BigDecimal longitude) {
        rastreamentoService.atualizarLocalizacao(idEntrega, latitude, longitude);
        return ResponseEntity.ok("Localização atualizada");
    }
}