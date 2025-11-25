package br.edu.pe.senac.pi_tads049.sprig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.pe.senac.pi_tads049.sprig.dto.RastreamentoDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Entregas;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EntregasRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para rastreamento de entregas por código
 * Implementa busca por código de rastreio único
 */
@Service
public class RastreamentoPorCodigoService {

    @Autowired
    private EntregasRepository entregasRepository;

    /**
     * Gera código de rastreio único para uma entrega
     * Formato: SPG-YYYYMMDD-XXXXX
     * SPG = Sprig, YYYYMMDD = data, XXXXX = ID da entrega
     */
    public String gerarCodigoRastreio(Entregas entrega) {
        LocalDate data = entrega.getDataPrevista();
        String dataStr = String.format("%04d%02d%02d", 
            data.getYear(), data.getMonthValue(), data.getDayOfMonth());
        String idStr = String.format("%05d", entrega.getIdEntregas());
        return "SPG-" + dataStr + "-" + idStr;
    }

    /**
     * Extrai ID da entrega a partir do código de rastreio
     * @param codigoRastreio Código no formato SPG-YYYYMMDD-XXXXX
     * @return ID da entrega
     */
    public Integer extrairIdDoCodigo(String codigoRastreio) {
        if (!codigoRastreio.matches("SPG-\\d{8}-\\d{5}")) {
            throw new IllegalArgumentException("Código inválido");
        }
        String idStr = codigoRastreio.substring(codigoRastreio.lastIndexOf('-') + 1);
        return Integer.parseInt(idStr);
    }


    /**
     * Rastreia entrega por código de rastreio
     * @param codigoRastreio Código único da entrega
     * @return DTO com informações de rastreamento
     */
    public RastreamentoDTO rastrearPorCodigo(String codigoRastreio) {
        Integer entregaId = extrairIdDoCodigo(codigoRastreio);
        Entregas entrega = entregasRepository.findById(entregaId)
            .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        
        // Valida código
        String codigoEsperado = gerarCodigoRastreio(entrega);
        if (!codigoRastreio.equals(codigoEsperado)) {
            throw new RuntimeException("Código não corresponde");
        }
        
        return converterParaDTO(entrega, codigoRastreio);
    }


    /**
     * Converte entrega para DTO de rastreamento
     */
    private RastreamentoDTO converterParaDTO(Entregas entrega, String codigoRastreio) {
        RastreamentoDTO dto = new RastreamentoDTO();
        dto.setIdEntrega(entrega.getIdEntregas());
        dto.setCodigoRastreio(codigoRastreio);
        dto.setStatus(entrega.getStatus().toString());
        dto.setDataPrevista(entrega.getDataPrevista());
        dto.setDataEntrega(entrega.getDataEntrega());
        
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
                if (entrega.getRota().getMotorista().getTelefones() != null 
                    && !entrega.getRota().getMotorista().getTelefones().isEmpty()) {
                    dto.setMotoristaTelefone(
                        entrega.getRota().getMotorista().getTelefones().get(0).getNumero()
                    );
                }
            }
        }
        
        // Destino
        if (entrega.getDestino() != null) {
            dto.setDestino(entrega.getDestino().getNomeDestino());
            dto.setLatitudeDestino(entrega.getDestino().getLatitude());
            dto.setLongitudeDestino(entrega.getDestino().getLongitude());
        }
        
        // Histórico (mock - pode ser implementado com tabela real)
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