package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Motorista;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.MotoristaRepository;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Transactional
    public Motorista cadastrarMotorista(Motorista motorista) {
        if (motoristaRepository.existsByCnh(motorista.getCnh())) {
            throw new RuntimeException("Já existe um motorista cadastrado com esta CNH");
        }
        return motoristaRepository.save(motorista);
    }

    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Optional<Motorista> buscarPorId(Integer id) {
        return motoristaRepository.findById(id);
    }

    public Optional<Motorista> buscarPorCnh(String cnh) {
        return motoristaRepository.findByCnh(cnh);
    }

    @Transactional
    public Motorista atualizarMotorista(Integer id, Motorista motoristaAtualizado) {
        Motorista motorista = motoristaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

        motorista.setNome(motoristaAtualizado.getNome());
        motorista.setCnh(motoristaAtualizado.getCnh());
        motorista.setTelefones(motoristaAtualizado.getTelefones());

        return motoristaRepository.save(motorista);
    }

    @Transactional
    public void deletarMotorista(Integer id) {
        if (!motoristaRepository.existsById(id)) {
            throw new RuntimeException("Motorista não encontrado");
        }
        motoristaRepository.deleteById(id);
    }
}
