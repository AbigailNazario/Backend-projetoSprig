package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.dto.EntregasDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.EntregasService;

@RestController
@RequestMapping("/entregas")
@CrossOrigin(origins = "*")
public class EntregasController {

    @Autowired
    private EntregasService entregasService;

    /**
     * Cria nova entrega
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> criar(@RequestBody EntregasDTO entregaDTO) {
        try {
            EntregasDTO novaEntrega = entregasService.criarEntrega(entregaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaEntrega);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Lista todas as entregas
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<List<EntregasDTO>> listarTodas() {
        return ResponseEntity.ok(entregasService.listarTodasDTO());
    }

    /**
     * Busca entrega por ID
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return entregasService.buscarPorId(id)
            .map(entrega -> ResponseEntity.ok(entrega))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza status de uma entrega
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Integer id,
            @RequestParam String novoStatus) {
        try {
            entregasService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok("Status atualizado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Cancela uma entrega
     * Acesso: GESTOR
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> cancelar(@PathVariable Integer id) {
        try {
            entregasService.cancelarEntrega(id);
            return ResponseEntity.ok("Entrega cancelada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}