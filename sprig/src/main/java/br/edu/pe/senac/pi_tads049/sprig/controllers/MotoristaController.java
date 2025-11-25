package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Motorista;
import br.edu.pe.senac.pi_tads049.sprig.service.MotoristaService;

@RestController
@RequestMapping("/motoristas")
@CrossOrigin(origins = "*")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> cadastrar(@RequestBody Motorista motorista) {
        try {
            Motorista novoMotorista = motoristaService.cadastrarMotorista(motorista);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoMotorista);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<List<Motorista>> listarTodos() {
        return ResponseEntity.ok(motoristaService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return motoristaService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnh/{cnh}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> buscarPorCnh(@PathVariable String cnh) {
        return motoristaService.buscarPorCnh(cnh)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Motorista motorista) {
        try {
            Motorista motoristaAtualizado = motoristaService.atualizarMotorista(id, motorista);
            return ResponseEntity.ok(motoristaAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            motoristaService.deletarMotorista(id);
            return ResponseEntity.ok("Motorista deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}