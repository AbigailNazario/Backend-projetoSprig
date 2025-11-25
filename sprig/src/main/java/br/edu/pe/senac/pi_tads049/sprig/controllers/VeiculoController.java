package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Veiculo;
import br.edu.pe.senac.pi_tads049.sprig.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> cadastrar(@RequestBody Veiculo veiculo) {
        try {
            Veiculo novoVeiculo = veiculoService.cadastrarVeiculo(veiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoVeiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return veiculoService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        return veiculoService.buscarPorPlaca(placa)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/capacidade/{capacidade}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<Veiculo>> buscarPorCapacidadeMinima(@PathVariable int capacidade) {
        return ResponseEntity.ok(veiculoService.buscarPorCapacidadeMinima(capacidade));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Veiculo veiculo) {
        try {
            Veiculo veiculoAtualizado = veiculoService.atualizarVeiculo(id, veiculo);
            return ResponseEntity.ok(veiculoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            veiculoService.deletarVeiculo(id);
            return ResponseEntity.ok("Veículo deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}