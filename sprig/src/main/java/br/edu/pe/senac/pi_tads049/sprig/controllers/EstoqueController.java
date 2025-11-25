package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.dto.ArmazemDTO;
import br.edu.pe.senac.pi_tads049.sprig.dto.LoteDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Estoque;
import br.edu.pe.senac.pi_tads049.sprig.service.ArmazemService;
import br.edu.pe.senac.pi_tads049.sprig.service.EstoqueService;
import br.edu.pe.senac.pi_tads049.sprig.service.LoteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estoque")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
public class EstoqueController {

    @Autowired
    private ArmazemService armazemService;

    @Autowired
    private LoteService loteService;
    
    @Autowired
    private EstoqueService estoqueService;

    /**
     * Lista todos os armazéns
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/armazens")
    public ResponseEntity<List<ArmazemDTO>> listarArmazens() {
        List<ArmazemDTO> armazens = armazemService.listarTodosDTO();
        return ResponseEntity.ok(armazens);
    }

    /**
     * Busca armazém por ID
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/armazens/{id}")
    public ResponseEntity<ArmazemDTO> buscarArmazem(@PathVariable Integer id) {
        ArmazemDTO armazem = armazemService.buscarPorIdDTO(id);
        return ResponseEntity.ok(armazem);
    }

    /**
     * Lista todos os lotes de sementes
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/lotes")
    public ResponseEntity<List<LoteDTO>> listarLotes() {
        List<LoteDTO> lotes = loteService.listarTodosDTO();
        return ResponseEntity.ok(lotes);
    }

    /**
     * Busca lotes por armazém
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/armazens/{armazemId}/lotes")
    public ResponseEntity<List<LoteDTO>> listarLotesPorArmazem(@PathVariable Integer armazemId) {
        List<LoteDTO> lotes = loteService.buscarPorArmazem(armazemId);
        return ResponseEntity.ok(lotes);
    }

    /**
     * Cria novo lote
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/lotes")
    public ResponseEntity<LoteDTO> criarLote(@Valid @RequestBody LoteDTO loteDTO) {
        LoteDTO novoLote = loteService.criar(loteDTO);
        return ResponseEntity.ok(novoLote);
    }

    /**
     * Atualiza quantidade de lote
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/lotes/{id}/quantidade")
    public ResponseEntity<?> atualizarQuantidade(
            @PathVariable Integer id,
            @RequestParam Integer novaQuantidade) {
        loteService.atualizarQuantidade(id, novaQuantidade);
        return ResponseEntity.ok("Quantidade atualizada com sucesso");
    }
    
    // ========== NOVOS ENDPOINTS DE ESTOQUE ==========
    
    /**
     * Lista todos os estoques
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodosEstoques() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }
    
    /**
     * Busca estoque por ID
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEstoquePorId(@PathVariable Integer id) {
        return estoqueService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Lista estoques por armazém
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/armazem/{armazemId}")
    public ResponseEntity<List<Estoque>> listarEstoquesPorArmazem(@PathVariable Integer armazemId) {
        return ResponseEntity.ok(estoqueService.listarPorArmazem(armazemId));
    }
    
    /**
     * Lista estoques abaixo do mínimo (alerta)
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/alertas/baixo")
    public ResponseEntity<List<Estoque>> listarEstoquesAbaixoDoMinimo() {
        return ResponseEntity.ok(estoqueService.listarEstoquesAbaixoDoMinimo());
    }
    
    /**
     * Lista estoques acima do máximo (alerta)
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/alertas/alto")
    public ResponseEntity<List<Estoque>> listarEstoquesAcimaDoMaximo() {
        return ResponseEntity.ok(estoqueService.listarEstoquesAcimaDoMaximo());
    }
    
    /**
     * Cria novo registro de estoque
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping
    public ResponseEntity<?> criarEstoque(@Valid @RequestBody Estoque estoque) {
        try {
            Estoque novoEstoque = estoqueService.criarEstoque(estoque);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEstoque);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Atualiza quantidade de estoque
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{id}/quantidade")
    public ResponseEntity<?> atualizarQuantidadeEstoque(
            @PathVariable Integer id,
            @RequestParam Integer novaQuantidade) {
        try {
            Estoque estoque = estoqueService.atualizarQuantidade(id, novaQuantidade);
            return ResponseEntity.ok(estoque);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Adiciona quantidade ao estoque
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/{id}/adicionar")
    public ResponseEntity<?> adicionarQuantidade(
            @PathVariable Integer id,
            @RequestParam Integer quantidade) {
        try {
            Estoque estoque = estoqueService.adicionarQuantidade(id, quantidade);
            return ResponseEntity.ok(estoque);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Remove quantidade do estoque
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/{id}/remover")
    public ResponseEntity<?> removerQuantidade(
            @PathVariable Integer id,
            @RequestParam Integer quantidade) {
        try {
            Estoque estoque = estoqueService.removerQuantidade(id, quantidade);
            return ResponseEntity.ok(estoque);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Atualiza limites de estoque
     * Acesso: GESTOR
     */
    @PutMapping("/{id}/limites")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> atualizarLimites(
            @PathVariable Integer id,
            @RequestParam Integer minimo,
            @RequestParam Integer maximo) {
        try {
            Estoque estoque = estoqueService.atualizarLimites(id, minimo, maximo);
            return ResponseEntity.ok(estoque);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Verifica status do estoque
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<String> verificarStatus(@PathVariable Integer id) {
        try {
            String status = estoqueService.verificarStatus(id);
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Deleta registro de estoque
     * Acesso: GESTOR
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletarEstoque(@PathVariable Integer id) {
        try {
            estoqueService.deletarEstoque(id);
            return ResponseEntity.ok("Estoque deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}