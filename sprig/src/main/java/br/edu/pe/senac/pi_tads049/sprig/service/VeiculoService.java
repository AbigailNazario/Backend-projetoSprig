package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Veiculo;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("Já existe um veículo cadastrado com esta placa");
        }
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> buscarPorId(Integer id) {
        return veiculoRepository.findById(id);
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa);
    }

    public List<Veiculo> buscarPorCapacidadeMinima(int capacidade) {
        return veiculoRepository.findByCapacidadeGreaterThanEqual(capacidade);
    }

    @Transactional
    public Veiculo atualizarVeiculo(Integer id, Veiculo veiculoAtualizado) {
        Veiculo veiculo = veiculoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        veiculo.setPlaca(veiculoAtualizado.getPlaca());
        veiculo.setModelo(veiculoAtualizado.getModelo());
        veiculo.setCapacidade(veiculoAtualizado.getCapacidade());

        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void deletarVeiculo(Integer id) {
        if (!veiculoRepository.existsById(id)) {
            throw new RuntimeException("Veículo não encontrado");
        }
        veiculoRepository.deleteById(id);
    }
}
