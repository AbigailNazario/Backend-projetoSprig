package br.edu.pe.senac.pi_tads049.sprig.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.dto.LoteDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.LoteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lotes")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
public class LoteController {

    @Autowired
    private LoteService loteService;

    /**
     * Lista todos os lotes
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping
    public ResponseEntity<List<LoteDTO>> listarTodos() {
        return ResponseEntity.ok(loteService.listarTodosDTO());
    }

    /**
     * Busca lote por ID
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return loteService.buscarPorId(id)
            .map(lote -> ResponseEntity.ok(lote))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista lotes disponíveis em estoque
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<?> listarDisponiveis() {
        return ResponseEntity.ok(loteService.listarLotesDisponiveis());
    }

    /**
     * Lista lotes próximos ao vencimento
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/proximos-vencimento")
    public ResponseEntity<?> listarProximosVencimento(
            @RequestParam(defaultValue = "30") int dias) {
        return ResponseEntity.ok(loteService.listarLotesProximosVencimento(dias));
    }

    /**
     * Cria novo lote
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody LoteDTO loteDTO) {
        try {
            LoteDTO novoLote = loteService.criar(loteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualiza quantidade de um lote
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{id}/quantidade")
    public ResponseEntity<?> atualizarQuantidade(
            @PathVariable Integer id,
            @RequestParam Integer novaQuantidade) {
        try {
            loteService.atualizarQuantidade(id, novaQuantidade);
            return ResponseEntity.ok("Quantidade atualizada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualiza status de um lote
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Integer id,
            @RequestParam String novoStatus) {
        try {
            loteService.atualizarStatus(id, 
                br.edu.pe.senac.pi_tads049.sprig.entidades.Status2.valueOf(novoStatus));
            return ResponseEntity.ok("Status atualizado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deleta um lote
     * Acesso: GESTOR apenas
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            loteService.deletarLote(id);
            return ResponseEntity.ok("Lote deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}