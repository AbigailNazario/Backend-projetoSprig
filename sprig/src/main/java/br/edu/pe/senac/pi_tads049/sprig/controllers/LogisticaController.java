package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.pe.senac.pi_tads049.sprig.dto.EntregasDTO;
import br.edu.pe.senac.pi_tads049.sprig.dto.RotaDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.EntregasService;
import br.edu.pe.senac.pi_tads049.sprig.service.RotaService;

@RestController
@RequestMapping("/logistica")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
public class LogisticaController {

    @Autowired
    private EntregasService entregasService;

    @Autowired
    private RotaService rotaService;

    /**
     * Cria nova entrega
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/entregas")
    public ResponseEntity<EntregasDTO> criarEntrega(@RequestBody EntregasDTO entregaDTO) {
        EntregasDTO novaEntrega = entregasService.criarEntrega(entregaDTO);
        return ResponseEntity.ok(novaEntrega);
    }

    /**
     * Lista todas as entregas
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/entregas")
    public ResponseEntity<List<EntregasDTO>> listarEntregas() {
        List<EntregasDTO> entregas = entregasService.listarTodasDTO();
        return ResponseEntity.ok(entregas);
    }

    /**
     * Atualiza status de uma entrega
     * Acesso: GESTOR, TECNICO
     */
    @PutMapping("/entregas/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Integer id,
            @RequestParam String novoStatus) {
        entregasService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok("Status atualizado com sucesso");
    }

    /**
     * Cria nova rota
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/rotas")
    public ResponseEntity<RotaDTO> criarRota(@RequestBody RotaDTO rotaDTO) {
        RotaDTO novaRota = rotaService.criarRota(rotaDTO);
        return ResponseEntity.ok(novaRota);
    }

    /**
     * Lista todas as rotas
     * Acesso: GESTOR, TECNICO
     */
    @GetMapping("/rotas")
    public ResponseEntity<List<RotaDTO>> listarRotas() {
        List<RotaDTO> rotas = rotaService.listarTodasDTO();
        return ResponseEntity.ok(rotas);
    }

    /**
     * Otimiza rota usando Google Maps API
     * Acesso: GESTOR, TECNICO
     */
    @PostMapping("/rotas/{id}/otimizar")
    public ResponseEntity<RotaDTO> otimizarRota(@PathVariable Integer id) {
        RotaDTO rotaOtimizada = rotaService.otimizarRota(id);
        return ResponseEntity.ok(rotaOtimizada);
    }
}