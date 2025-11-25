package br.edu.pe.senac.pi_tads049.sprig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregasDTO {
    private Integer idEntregas;
    private LocalDate dataPrevista;
    private LocalDate dataEntrega;
    private Integer quantidadeEntregue;
    private String status;
    
    // Informações do Lote
    private Integer loteId;
    private String loteEspecie;
    private Integer loteNumero;
    
    // Informações da Rota
    private Integer rotaId;
    private Double rotaDistancia;
    private Double rotaTempoEstimado;
    
    // Informações do Destino
    private Integer destinoId;
    private String destinoNome;
    private String destinoCidade;
    private String destinoEstado;
    
    // Informações do Motorista (via Rota)
    private String motoristaNome;
    private String motoristaTelefone;
}
