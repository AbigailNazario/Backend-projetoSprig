package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.dto.RotaDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.RotaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rotas")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    /**
     * Lista todas as rotas
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping
    public ResponseEntity<List<RotaDTO>> listarTodas() {
        return ResponseEntity.ok(rotaService.listarTodasDTO());
    }

    /**
     * Busca rota por ID
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return rotaService.buscarPorId(id)
            .map(rota -> ResponseEntity.ok(rota))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista rotas ordenadas por distância
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/ordenadas-distancia")
    public ResponseEntity<?> listarOrdenadasPorDistancia() {
        return ResponseEntity.ok(rotaService.listarRotasOrdenadasPorDistancia());
    }

    /**
     * Busca rotas por distância máxima
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/distancia-maxima/{distancia}")
    public ResponseEntity<?> buscarPorDistanciaMaxima(@PathVariable double distancia) {
        return ResponseEntity.ok(rotaService.buscarRotasPorDistanciaMaxima(distancia));
    }

    /**
     * Cria nova rota
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody RotaDTO rotaDTO) {
        try {
            RotaDTO novaRota = rotaService.criarRota(rotaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaRota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Otimiza uma rota existente
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/{id}/otimizar")
    public ResponseEntity<?> otimizar(@PathVariable Integer id) {
        try {
            RotaDTO rotaOtimizada = rotaService.otimizarRota(id);
            return ResponseEntity.ok(rotaOtimizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Atualiza uma rota
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody RotaDTO rotaDTO) {
        try {
            // Converter DTO para entidade
            br.edu.pe.senac.pi_tads049.sprig.entidades.Rota rota = 
                new br.edu.pe.senac.pi_tads049.sprig.entidades.Rota();
            rota.setDistancia(rotaDTO.getDistancia());
            rota.setTempoEstimado(rotaDTO.getTempoEstimado());
            rota.setCustoEstimado(rotaDTO.getCustoEstimado());
            
            br.edu.pe.senac.pi_tads049.sprig.entidades.Rota rotaAtualizada = 
                rotaService.atualizarRota(id, rota);
            return ResponseEntity.ok(rotaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deleta uma rota
     * Acesso: GESTOR apenas
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            rotaService.deletarRota(id);
            return ResponseEntity.ok("Rota deletada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}