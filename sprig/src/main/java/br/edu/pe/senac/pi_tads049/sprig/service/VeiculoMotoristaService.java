package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Motorista;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Veiculo;
import br.edu.pe.senac.pi_tads049.sprig.entidades.VeiculoMotorista;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.MotoristaRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.VeiculoMotoristaRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.VeiculoRepository;
import jakarta.transaction.Transactional;

@Service
public class VeiculoMotoristaService {
    
    @Autowired
    private VeiculoMotoristaRepository repository;
    
    @Autowired
    private VeiculoRepository veiculoRepository;
    
    @Autowired
    private MotoristaRepository motoristaRepository;
    
    @Transactional
    public VeiculoMotorista vincular(Integer veiculoId, Integer motoristaId) {
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
            .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        
        Motorista motorista = motoristaRepository.findById(motoristaId)
            .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));
        
        VeiculoMotorista vinculo = new VeiculoMotorista();
        vinculo.setVeiculo(veiculo);
        vinculo.setMotorista(motorista);
        
        return repository.save(vinculo);
    }
    
    public List<VeiculoMotorista> listarPorMotorista(Integer idMotorista) {
        return repository.findByMotoristaIdMotorista(idMotorista);
    }
    
    public List<VeiculoMotorista> listarPorVeiculo(Integer idVeiculo) {
        return repository.findByVeiculoIdVeiculo(idVeiculo);
    }
    
    @Transactional
    public void desvincular(Integer id) {
        repository.deleteById(id);
    }
}