package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.dto.RotaDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Rota;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.RotaRepository;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;
    
    @Autowired
    private OtimizacaoRotaService otimizacaoRotaService;

    @Transactional
    public Rota cadastrarRota(Rota rota) {
        return rotaRepository.save(rota);
    }
    
    @Transactional
    public RotaDTO criarRota(RotaDTO dto) {
        Rota rota = new Rota();
        rota.setDataSaida(dto.getDataSaida());
        rota.setDataRetorno(dto.getDataRetorno());
        rota.setDistancia(dto.getDistancia());
        rota.setTempoEstimado(dto.getTempoEstimado());
        rota.setCustoEstimado(dto.getCustoEstimado());
        
        Rota saved = rotaRepository.save(rota);
        return converterParaDTO(saved);
    }

    public List<Rota> listarTodas() {
        return rotaRepository.findAll();
    }
    
    public List<RotaDTO> listarTodasDTO() {
        return rotaRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<Rota> buscarPorId(Integer id) {
        return rotaRepository.findById(id);
    }

    public List<Rota> listarPorDestino(Destino destino) {
        return rotaRepository.findByDestino(destino);
    }

    public List<Rota> listarRotasOrdenadasPorDistancia() {
        return rotaRepository.findAllByOrderByDistanciaAsc();
    }

    public List<Rota> buscarRotasPorDistanciaMaxima(double distanciaMaxima) {
        return rotaRepository.findByDistanciaLessThanEqual(distanciaMaxima);
    }

    @Transactional
    public Rota atualizarRota(Integer id, Rota rotaAtualizada) {
        Rota rota = rotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rota não encontrada"));

        rota.setDistancia(rotaAtualizada.getDistancia());
        rota.setTempoEstimado(rotaAtualizada.getTempoEstimado());
        rota.setDestino(rotaAtualizada.getDestino());

        return rotaRepository.save(rota);
    }

    @Transactional
    public void deletarRota(Integer id) {
        if (!rotaRepository.existsById(id)) {
            throw new RuntimeException("Rota não encontrada");
        }
        rotaRepository.deleteById(id);
    }
    
    /**
     * Otimiza rota usando Google Maps API ou cálculo estimado
     * FUNCIONALIDADE IMPLEMENTADA: 70% → 100%
     */
    @Transactional
    public RotaDTO otimizarRota(Integer id) {
        Rota rota = rotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rota não encontrada"));
        
        // Usa o serviço de otimização real
        Rota rotaOtimizada = otimizacaoRotaService.otimizarRotaComGoogleMaps(rota);
        
        return converterParaDTO(rotaOtimizada);
    }
    
    private RotaDTO converterParaDTO(Rota rota) {
        RotaDTO dto = new RotaDTO();
        dto.setId(rota.getIdRota());
        dto.setDataSaida(rota.getDataSaida());
        dto.setDataRetorno(rota.getDataRetorno());
        dto.setDistancia(rota.getDistancia());
        dto.setTempoEstimado(rota.getTempoEstimado());
        dto.setCustoEstimado(rota.getCustoEstimado());
        
        if (rota.getMotorista() != null) {
            dto.setMotoristaId(rota.getMotorista().getIdMotorista());
            dto.setMotoristaNome(rota.getMotorista().getNome());
            dto.setMotoristaCnh(rota.getMotorista().getCnh());
        }
        
        if (rota.getArmazemOrigem() != null) {
            dto.setIdArmazemOrigem(rota.getArmazemOrigem().getIdArmazem());
            dto.setArmazemOrigemNome(rota.getArmazemOrigem().getNome());
        }
        
        if (rota.getDestino() != null) {
            dto.setIdDestino(rota.getDestino().getIdDestino());
            dto.setDestinoNome(rota.getDestino().getNomeDestino());
        }
        
        return dto;
    }
}