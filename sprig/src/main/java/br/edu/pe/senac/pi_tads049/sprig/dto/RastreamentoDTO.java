package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RastreamentoDTO {
    private Integer idEntrega;
    private String codigoRastreio; // NOVO: Código único de rastreio
    private String status;
    private LocalDate dataPrevista;
    private LocalDate dataEntrega;
    
    // Informações do Lote
    private String loteEspecie;
    private Integer loteQuantidade;
    
    // Informações da Rota
    private Double distanciaTotal;
    private Double tempoEstimado;
    
    // Informações de Origem e Destino
    private String origem;
    private String destino;
    private BigDecimal latitudeDestino;
    private BigDecimal longitudeDestino;
    
    // Informações do Motorista
    private String motoristaNome;
    private String motoristaTelefone;
    
    // Histórico de Status
    private List<HistoricoStatusDTO> historico;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoricoStatusDTO {
        private LocalDate data;
        private String status;
        private String observacao;
    }

	
}