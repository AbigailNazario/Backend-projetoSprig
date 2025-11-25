package br.edu.pe.senac.pi_tads049.sprig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RotaDTO {
    private Integer id;
    private LocalDate dataSaida;
    private LocalDate dataRetorno;
    private Double distancia;
    private Double tempoEstimado;
    private BigDecimal custoEstimado;
    
    // Informações do Motorista
    private Integer motoristaId;
    private String motoristaNome;
    private String motoristaCnh;
    
    // Informações do Armazém de Origem
    private Integer idArmazemOrigem;
    private String armazemOrigemNome;
    
    // Informações do Destino
    private Integer idDestino;
    private String destinoNome;
    private String destinoCidade;
    private String destinoEstado;
}