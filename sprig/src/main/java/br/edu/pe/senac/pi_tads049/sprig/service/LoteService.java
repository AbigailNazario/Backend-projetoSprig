package br.edu.pe.senac.pi_tads049.sprig.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.dto.LoteDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Lote;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Status2;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.ArmazemRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.LoteRepository;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;
    
    @Autowired
    private ArmazemRepository armazemRepository;

    @Transactional
    public Lote cadastrarLote(Lote lote) {
        lote.setStatus(Status2.Em_Estoque);
        return loteRepository.save(lote);
    }
    
    @Transactional
    public LoteDTO criar(LoteDTO dto) {
        Lote lote = new Lote();
        lote.setNumeroLote(dto.getNumeroLote());
        lote.setEspecie(dto.getEspecie());
        lote.setQuantidade(dto.getQuantidade());
        lote.setValidade(dto.getValidade());
        lote.setDataRecebimento(dto.getDataRecebimento());
        lote.setStatus(Status2.valueOf(dto.getStatus()));
        lote.setQrCode(dto.getQrCode());
        
        Lote saved = loteRepository.save(lote);
        return converterParaDTO(saved);
    }

    public List<Lote> listarTodos() {
        return loteRepository.findAll();
    }
    
    public List<LoteDTO> listarTodosDTO() {
        return loteRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<Lote> buscarPorId(Integer id) {
        return loteRepository.findById(id);
    }
    
    public List<LoteDTO> buscarPorArmazem(Integer armazemId) {
        return loteRepository.findAll().stream()
                .filter(lote -> lote.getArmazem() != null && lote.getArmazem().getIdArmazem() == armazemId)
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<Lote> listarLotesDisponiveis() {
        return loteRepository.findByStatusOrderByValidadeAsc(Status2.Em_Estoque);
    }

    public List<Lote> listarLotesProximosVencimento(int diasAntecedencia) {
        LocalDate dataLimite = LocalDate.now().plusDays(diasAntecedencia);
        return loteRepository.findLotesProximosVencimento(dataLimite, Status2.Em_Estoque);
    }

    @Transactional
    public Lote atualizarStatus(Integer id, Status2 novoStatus) {
        Lote lote = loteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado"));
        lote.setStatus(novoStatus);
        return loteRepository.save(lote);
    }

    @Transactional
    public Lote atualizarQuantidade(Integer id, int novaQuantidade) {
        Lote lote = loteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado"));
        
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }
        
        lote.setQuantidade(novaQuantidade);
        return loteRepository.save(lote);
    }
    
    @Transactional
    public void atualizarQuantidade(Integer id, Integer novaQuantidade) {
        Lote lote = loteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado"));
        
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }
        
        lote.setQuantidade(novaQuantidade);
        loteRepository.save(lote);
    }

    @Transactional
    public void deletarLote(Integer id) {
        if (!loteRepository.existsById(id)) {
            throw new RuntimeException("Lote não encontrado");
        }
        loteRepository.deleteById(id);
    }
    
    private LoteDTO converterParaDTO(Lote lote) {
        LoteDTO dto = new LoteDTO();
        dto.setId(lote.getIdLote());
        dto.setNumeroLote(lote.getNumeroLote());
        dto.setEspecie(lote.getEspecie());
        dto.setQuantidade(lote.getQuantidade());
        dto.setValidade(lote.getValidade());
        dto.setDataRecebimento(lote.getDataRecebimento());
        dto.setStatus(lote.getStatus().toString());
        dto.setQrCode(lote.getQrCode());
        
        if (lote.getArmazem() != null) {
            dto.setArmazemId(lote.getArmazem().getIdArmazem());
            dto.setArmazemNome(lote.getArmazem().getNome());
            dto.setArmazemCapacidade(lote.getArmazem().getCapacidadeTotal());
        }
        
        if (lote.getFornecedor() != null) {
            dto.setFornecedorCnpj(lote.getFornecedor().getCnpj());
            dto.setFornecedorNome(lote.getFornecedor().getNome());
        }
        
        return dto;
    }
}