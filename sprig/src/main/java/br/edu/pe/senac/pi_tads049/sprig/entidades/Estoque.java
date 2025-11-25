package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estoque")
public class Estoque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstoque;
    
    @Column(nullable = false)
    private Integer quantidadeAtual;
    
    @Column(nullable = false)
    private Integer quantidadeMinima;
    
    @Column(nullable = false)
    private Integer quantidadeMaxima;
    
    @Column
    private LocalDate ultimaAtualizacao;
    
    @Column
    private String observacoes;
    
    @ManyToOne
    @JoinColumn(name = "fk_armazem", nullable = false)
    private Armazem armazem;
    
    @ManyToOne
    @JoinColumn(name = "fk_lote")
    private Lote lote;
    
    /**
     * Verifica se o estoque está abaixo do mínimo
     */
    public boolean isAbaixoDoMinimo() {
        return quantidadeAtual < quantidadeMinima;
    }
    
    /**
     * Verifica se o estoque está acima do máximo
     */
    public boolean isAcimaDoMaximo() {
        return quantidadeAtual > quantidadeMaxima;
    }
    
    /**
     * Calcula a porcentagem de ocupação do estoque
     */
    public double getPorcentagemOcupacao() {
        if (quantidadeMaxima == 0) return 0;
        return (quantidadeAtual * 100.0) / quantidadeMaxima;
    }
}