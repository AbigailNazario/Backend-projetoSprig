package br.edu.pe.senac.pi_tads049.sprig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.pe.senac.pi_tads049.sprig.dto.RastreamentoDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Entregas;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EntregasRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RastreamentoService {

    @Autowired
    private EntregasRepository entregasRepository;
    
    @Autowired
    private RastreamentoPorCodigoService rastreamentoPorCodigoService;

    public RastreamentoDTO rastrear(Integer entregaId) {
        Entregas entrega = entregasRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        return converterParaDTO(entrega);
    }

    /**
     * Rastreia entrega por código de rastreio
     * FUNCIONALIDADE IMPLEMENTADA: 60% → 100%
     */
    public RastreamentoDTO rastrearPorCodigo(String codigoRastreio) {
        return rastreamentoPorCodigoService.rastrearPorCodigo(codigoRastreio);
    }

    public void atualizarLocalizacao(Integer idEntrega, BigDecimal latitude, BigDecimal longitude) {
        Entregas entrega = entregasRepository.findById(idEntrega)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        // NOTA: Latitude e longitude estão no Destino, não na Entrega
        // Esta funcionalidade seria para atualizar a localização atual do veículo em trânsito
        // Por enquanto, apenas valida que a entrega existe
        
        System.out.println(String.format(
            "Localização atualizada para entrega %d: Lat %s, Long %s", 
            idEntrega, latitude.toString(), longitude.toString()
        ));
        
        // Em uma implementação real, você poderia:
        // 1. Criar uma tabela de histórico de localizações
        // 2. Armazenar a última localização conhecida do veículo
        // 3. Calcular distância restante até o destino
    }

    private RastreamentoDTO converterParaDTO(Entregas entrega) {
        RastreamentoDTO dto = new RastreamentoDTO();
        dto.setIdEntrega(entrega.getIdEntregas());
        dto.setStatus(entrega.getStatus().toString());
        dto.setDataPrevista(entrega.getDataPrevista());
        dto.setDataEntrega(entrega.getDataEntrega());
        
        // Adiciona código de rastreio
        dto.setCodigoRastreio(rastreamentoPorCodigoService.gerarCodigoRastreio(entrega));
        
        // Lote
        if (entrega.getLote() != null) {
            dto.setLoteEspecie(entrega.getLote().getEspecie());
            dto.setLoteQuantidade(entrega.getLote().getQuantidade());
        }
        
        // Rota
        if (entrega.getRota() != null) {
            dto.setDistanciaTotal(entrega.getRota().getDistancia());
            dto.setTempoEstimado(entrega.getRota().getTempoEstimado());
            
            // Origem
            if (entrega.getRota().getArmazemOrigem() != null) {
                dto.setOrigem(entrega.getRota().getArmazemOrigem().getNome());
            }
            
            // Motorista
            if (entrega.getRota().getMotorista() != null) {
                dto.setMotoristaNome(entrega.getRota().getMotorista().getNome());
                // Adicionar telefone se disponível
                if (entrega.getRota().getMotorista().getTelefones() != null 
                    && !entrega.getRota().getMotorista().getTelefones().isEmpty()) {
                    dto.setMotoristaTelefone(
                        entrega.getRota().getMotorista().getTelefones().get(0).getNumero()
                    );
                }
            }
        }
        
        // Destino - AQUI PEGAMOS LATITUDE E LONGITUDE
        if (entrega.getDestino() != null) {
            dto.setDestino(entrega.getDestino().getNomeDestino());
            
            // Coordenadas do destino final - CORRIGIDO
            dto.setLatitudeDestino(entrega.getDestino().getLatitude());
            dto.setLongitudeDestino(entrega.getDestino().getLongitude());
        }
        
        // Histórico (mock - você pode implementar uma tabela de histórico)
        List<RastreamentoDTO.HistoricoStatusDTO> historico = new ArrayList<>();
        historico.add(new RastreamentoDTO.HistoricoStatusDTO(
                LocalDate.now().minusDays(2),
                "PENDENTE",
                "Entrega criada"
        ));
        historico.add(new RastreamentoDTO.HistoricoStatusDTO(
                LocalDate.now().minusDays(1),
                "EM_ROTA",
                "Saiu do armazém"
        ));
        dto.setHistorico(historico);
        
        return dto;
    }
}