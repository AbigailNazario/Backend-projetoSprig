package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Tipo;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.DestinoRepository;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository destinoRepository;

    @Transactional
    public Destino cadastrarDestino(Destino destino) {
        return destinoRepository.save(destino);
    }

    public List<Destino> listarTodos() {
        return destinoRepository.findAll();
    }

    public Optional<Destino> buscarPorId(Integer id) {
        return destinoRepository.findById(id);
    }

    public List<Destino> listarPorTipo(Tipo tipo) {
        return destinoRepository.findByTipo(tipo);
    }

    public List<Destino> buscarPorNome(String nomeDestino) {
        return destinoRepository.findByNomeDestinoContainingIgnoreCase(nomeDestino);
    }

    @Transactional
    public Destino atualizarDestino(Integer id, Destino destinoAtualizado) {
        Destino destino = destinoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Destino não encontrado"));

        destino.setNomeDestino(destinoAtualizado.getNomeDestino());
        destino.setTipo(destinoAtualizado.getTipo());
        destino.setLatitude(destinoAtualizado.getLatitude());
        destino.setLongitude(destinoAtualizado.getLongitude());
        destino.setEnderecos(destinoAtualizado.getEnderecos());

        return destinoRepository.save(destino);
    }

    @Transactional
    public void deletarDestino(Integer id) {
        if (!destinoRepository.existsById(id)) {
            throw new RuntimeException("Destino não encontrado");
        }
        destinoRepository.deleteById(id);
    }
}
