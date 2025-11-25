package br.edu.pe.senac.pi_tads049.sprig.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.dto.EntregasDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Entregas;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Status;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Usuario;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EntregasRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.UsuarioRepository;

@Service
public class EntregasService {

    @Autowired
    private EntregasRepository entregasRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Entregas criarEntrega(Entregas entrega) {
        entrega.setStatus(Status.Em_rota);
        entrega.setDataEntrega(LocalDate.now());
        return entregasRepository.save(entrega);
    }
    
    @Transactional
    public EntregasDTO criarEntrega(EntregasDTO dto) {
        // Converter DTO para Entidade e salvar
        Entregas entrega = new Entregas();
        entrega.setDataPrevista(dto.getDataPrevista());
        entrega.setQuantidadeEntregue(dto.getQuantidadeEntregue());
        entrega.setStatus(Status.valueOf(dto.getStatus()));
        
        Entregas saved = entregasRepository.save(entrega);
        return converterParaDTO(saved);
    }

    public List<Entregas> listarTodas() {
        return entregasRepository.findAll();
    }
    
    public List<EntregasDTO> listarTodasDTO() {
        return entregasRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<Entregas> buscarPorId(Integer id) {
        return entregasRepository.findById(id);
    }

    public List<Entregas> listarPorStatus(Status status) {
        return entregasRepository.findByStatus(status);
    }

    public List<Entregas> listarEntregasEmRota() {
        return entregasRepository.findByStatus(Status.Em_rota);
    }

    public List<Entregas> listarEntregasAtrasadas() {
        return entregasRepository.findByStatus(Status.Atraso);
    }

    public List<Entregas> listarPorDestino(Destino destino) {
        return entregasRepository.findByDestino(destino);
    }

    public List<Entregas> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return entregasRepository.findEntregasPorPeriodo(dataInicio, dataFim);
    }

    @Transactional
    public Entregas atualizarStatus(Integer id, Status novoStatus) {
        Entregas entrega = entregasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        entrega.setStatus(novoStatus);
        
        if (novoStatus == Status.Entregue) {
            entrega.setDataEntrega(LocalDate.now());
        }
        
        return entregasRepository.save(entrega);
    }
    
    @Transactional
    public void atualizarStatus(Integer id, String novoStatus) {
        Entregas entrega = entregasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        Status status = Status.valueOf(novoStatus);
        entrega.setStatus(status);
        
        if (status == Status.Entregue) {
            entrega.setDataEntrega(LocalDate.now());
        }
        
        entregasRepository.save(entrega);
    }

    @Transactional
    public void cancelarEntrega(Integer id) {
        if (!entregasRepository.existsById(id)) {
            throw new RuntimeException("Entrega não encontrada");
        }
        entregasRepository.deleteById(id);
    }
    
    // Métodos para Agricultor
    public List<EntregasDTO> buscarEntregasPorAgricultor(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Buscar entregas onde o destino está relacionado ao agricultor
        return entregasRepository.findAll().stream()
                .filter(e -> e.getDestino() != null && 
                            e.getDestino().getAgricultor() != null &&
                            e.getDestino().getAgricultor().equals(usuario))
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    public EntregasDTO buscarEntregaPorIdEAgricultor(Integer id, String email) {
        Entregas entrega = entregasRepository.findById(id)
        		
            .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        // Validar se a entrega pertence ao agricultor
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // CORREÇÃO: Usar AccessDeniedException do Spring Security
        if (entrega.getDestino() == null || 
            entrega.getDestino().getAgricultor() == null ||
            !entrega.getDestino().getAgricultor().equals(usuario)) {
		    throw new AccessDeniedException("Entrega não pertence ao usuário");
		}
        
        return converterParaDTO(entrega);
    }
    
    @Transactional
    public void confirmarRecebimento(Integer id, String email) {
        Entregas entrega = entregasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        // Validar se a entrega pertence ao agricultor
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (entrega.getDestino() == null || 
            entrega.getDestino().getAgricultor() == null ||
            !entrega.getDestino().getAgricultor().equals(usuario)) {
            throw new AccessDeniedException("Entrega não pertence ao usuário");
        }
        
        entrega.setStatus(Status.Entregue);
        entrega.setDataEntrega(LocalDate.now());
        entregasRepository.save(entrega);
    }
    
    // Converter Entidade para DTO
    private EntregasDTO converterParaDTO(Entregas entrega) {
        EntregasDTO dto = new EntregasDTO();
        dto.setIdEntregas(entrega.getIdEntregas());
        dto.setDataPrevista(entrega.getDataPrevista());
        dto.setDataEntrega(entrega.getDataEntrega());
        dto.setQuantidadeEntregue(entrega.getQuantidadeEntregue());
        dto.setStatus(entrega.getStatus().toString());
        
        // Lote
        if (entrega.getLote() != null) {
            dto.setLoteId(entrega.getLote().getIdLote());
            dto.setLoteEspecie(entrega.getLote().getEspecie());
            dto.setLoteNumero(entrega.getLote().getNumeroLote());
        }
        
        // Rota
        if (entrega.getRota() != null) {
            dto.setRotaId(entrega.getRota().getIdRota());
            dto.setRotaDistancia(entrega.getRota().getDistancia());
            dto.setRotaTempoEstimado(entrega.getRota().getTempoEstimado());
            
            // Motorista via Rota
            if (entrega.getRota().getMotorista() != null) {
                dto.setMotoristaNome(entrega.getRota().getMotorista().getNome());
                // Adicionar telefone se disponível
            }
        }
        
        // Destino
        if (entrega.getDestino() != null) {
            dto.setDestinoId(entrega.getDestino().getIdDestino());
            dto.setDestinoNome(entrega.getDestino().getNomeDestino());
            // Adicionar cidade e estado se disponível nos endereços
        }
        
        return dto;
    }
}