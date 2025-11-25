package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.dto.DestinoDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Tipo;
import br.edu.pe.senac.pi_tads049.sprig.service.DestinoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/destinos")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    /**
     * Lista todos os destinos
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping
    public ResponseEntity<List<Destino>> listarTodos() {
        return ResponseEntity.ok(destinoService.listarTodos());
    }

    /**
     * Busca destino por ID
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return destinoService.buscarPorId(id)
            .map(destino -> ResponseEntity.ok(destino))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista destinos por tipo
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> listarPorTipo(@PathVariable String tipo) {
        try {
            Tipo tipoEnum = Tipo.valueOf(tipo);
            return ResponseEntity.ok(destinoService.listarPorTipo(tipoEnum));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Tipo inválido. Use: Fazenda, Cooperativa, Armazem");
        }
    }

    /**
     * Busca destinos por nome
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(destinoService.buscarPorNome(nome));
    }

    /**
     * Cria novo destino
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody DestinoDTO destinoDTO) {
        try {
            Destino destino = new Destino();
            destino.setNomeDestino(destinoDTO.getNomeDestino());
            destino.setLatitude(destinoDTO.getLatitude());
            destino.setLongitude(destinoDTO.getLongitude());
            destino.setTipo(Tipo.valueOf(destinoDTO.getTipo()));
            
            Destino novoDestino = destinoService.cadastrarDestino(destino);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoDestino);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualiza um destino
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DestinoDTO destinoDTO) {
        try {
            Destino destino = new Destino();
            destino.setNomeDestino(destinoDTO.getNomeDestino());
            destino.setLatitude(destinoDTO.getLatitude());
            destino.setLongitude(destinoDTO.getLongitude());
            destino.setTipo(Tipo.valueOf(destinoDTO.getTipo()));
            
            Destino destinoAtualizado = destinoService.atualizarDestino(id, destino);
            return ResponseEntity.ok(destinoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deleta um destino
     * Acesso: GESTOR apenas
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            destinoService.deletarDestino(id);
            return ResponseEntity.ok("Destino deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}