package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.dto.ArmazemDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Armazem;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Usuario;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.ArmazemRepository;

@Service
public class ArmazemService {

    @Autowired
    private ArmazemRepository armazemRepository;

    @Transactional
    public Armazem cadastrarArmazem(Armazem armazem) {
        return armazemRepository.save(armazem);
    }

    public List<Armazem> listarTodos() {
        return armazemRepository.findAll();
    }
    
    public List<ArmazemDTO> listarTodosDTO() {
        return armazemRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<Armazem> buscarPorId(Integer idArmazem) {
        return armazemRepository.findById(idArmazem);
    }
    
    public ArmazemDTO buscarPorIdDTO(Integer idArmazem) {
        Armazem armazem = armazemRepository.findById(idArmazem)
                .orElseThrow(() -> new RuntimeException("Armazém não encontrado"));
        return converterParaDTO(armazem);
    }

    public Optional<Armazem> buscarPorNome(String nome) {
        return armazemRepository.findByNome(nome);
    }

    public List<Armazem> buscarPorResponsavel(Usuario responsavel) {
        return armazemRepository.findByResponsavel(responsavel);
    }

    public List<Armazem> buscarPorCapacidadeMinima(int capacidade) {
        return armazemRepository.findByCapacidadeTotalGreaterThanEqual(capacidade);
    }

    @Transactional
    public Armazem atualizarArmazem(Integer idArmazem, Armazem armazemAtualizado) {
        Armazem armazem = armazemRepository.findById(idArmazem)
            .orElseThrow(() -> new RuntimeException("Armazém não encontrado"));

        armazem.setNome(armazemAtualizado.getNome());
        armazem.setCapacidadeTotal(armazemAtualizado.getCapacidadeTotal());
        armazem.setEnderecos(armazemAtualizado.getEnderecos());
        armazem.setResponsavel(armazemAtualizado.getResponsavel());

        return armazemRepository.save(armazem);
    }

    @Transactional
    public void deletarArmazem(Integer idArmazem) {
        if (!armazemRepository.existsById(idArmazem)) {
            throw new RuntimeException("Armazém não encontrado");
        }
        armazemRepository.deleteById(idArmazem);
    }
    
    private ArmazemDTO converterParaDTO(Armazem armazem) {
        ArmazemDTO dto = new ArmazemDTO();
        dto.setIdArmazem(armazem.getIdArmazem());
        dto.setNome(armazem.getNome());
        dto.setCapacidadeTotal(armazem.getCapacidadeTotal());
        
        if (armazem.getResponsavel() != null) {
            dto.setResponsavelCpf(armazem.getResponsavel().getCpf());
            dto.setResponsavelNome(armazem.getResponsavel().getNome());
            dto.setResponsavelEmail(armazem.getResponsavel().getEmail());
        }
        
        if (armazem.getEnderecos() != null && !armazem.getEnderecos().isEmpty()) {
            dto.setEnderecos(armazem.getEnderecos().stream()
                    .map(end -> new ArmazemDTO.EnderecoADTO(
                    		end.getIdEnderecoA(),
                            end.getNumero(),
                            end.getRua(),
                            end.getCep(),
                            end.getLogradouro(),
                    		end.getBairro(),    
                    		end.getCidade(),  
                    		end.getEstado()
                    ))
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}