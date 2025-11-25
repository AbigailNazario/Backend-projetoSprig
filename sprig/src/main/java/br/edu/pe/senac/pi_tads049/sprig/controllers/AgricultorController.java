package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.pe.senac.pi_tads049.sprig.dto.EntregasDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.EntregasService;

@RestController
@RequestMapping("/agricultor")
public class AgricultorController {

    @Autowired
    private EntregasService entregasService;

    /**
     * Lista todas as entregas destinadas ao agricultor logado
     * Acesso: AGRICULTOR
     */
    @GetMapping("/minhas-entregas")
    public ResponseEntity<List<EntregasDTO>> listarMinhasEntregas(Authentication authentication) {
        String email = authentication.getName();
        List<EntregasDTO> entregas = entregasService.buscarEntregasPorAgricultor(email);
        return ResponseEntity.ok(entregas);
    }

    /**
     * Busca detalhes de uma entrega específica
     * Acesso: AGRICULTOR (apenas suas próprias entregas)
     */
    @GetMapping("/entregas/{id}")
    public ResponseEntity<EntregasDTO> buscarEntrega(
            @PathVariable Integer id,
            Authentication authentication) {
        String email = authentication.getName();
        EntregasDTO entrega = entregasService.buscarEntregaPorIdEAgricultor(id, email);
        return ResponseEntity.ok(entrega);
    }

    /**
     * Confirma recebimento de uma entrega
     * Acesso: AGRICULTOR
     */
    @PutMapping("/entregas/{id}/confirmar-recebimento")
    public ResponseEntity<?> confirmarRecebimento(
            @PathVariable Integer id,
            Authentication authentication) {
        String email = authentication.getName();
        entregasService.confirmarRecebimento(id, email);
        return ResponseEntity.ok("Recebimento confirmado com sucesso");
    }
}