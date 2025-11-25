package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Armazem;
import br.edu.pe.senac.pi_tads049.sprig.service.ArmazemService;

@RestController
@RequestMapping("/armazens")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
public class ArmazemController {

    @Autowired
    private ArmazemService armazemService;

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> cadastrar(@RequestBody Armazem armazem) {
        try {
            Armazem novoArmazem = armazemService.cadastrarArmazem(armazem);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoArmazem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<List<Armazem>> listarTodos() {
        return ResponseEntity.ok(armazemService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return armazemService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        return armazemService.buscarPorNome(nome)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/capacidade/{capacidade}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<Armazem>> buscarPorCapacidadeMinima(@PathVariable int capacidade) {
        return ResponseEntity.ok(armazemService.buscarPorCapacidadeMinima(capacidade));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Armazem armazem) {
        try {
            Armazem armazemAtualizado = armazemService.atualizarArmazem(id, armazem);
            return ResponseEntity.ok(armazemAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            armazemService.deletarArmazem(id);
            return ResponseEntity.ok("Armazém deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}