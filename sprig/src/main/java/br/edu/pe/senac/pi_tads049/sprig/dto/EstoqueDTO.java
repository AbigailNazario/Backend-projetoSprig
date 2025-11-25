package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Integer idEstoque;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private Integer quantidadeMaxima;
    private LocalDate ultimaAtualizacao;
    private String observacoes;
    private String status; // NORMAL, BAIXO, ALTO
    private Double porcentagemOcupacao;
    
    // Informações do Armazém
    private Integer armazemId;
    private String armazemNome;
    private Integer armazemCapacidade;
    
    // Informações do Lote
    private Integer loteId;
    private Integer loteNumero;
    private String loteEspecie;
}