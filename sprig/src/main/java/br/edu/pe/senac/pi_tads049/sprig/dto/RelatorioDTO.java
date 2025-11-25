package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import br.edu.pe.senac.pi_tads049.sprig.dto.RastreamentoDTO.HistoricoStatusDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDTO {
	
	private String titulo;
    private String conteudoDetalhes;
	
	private Integer idRelatorio;
	private LocalDate dataGeracao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer totalEntregas;
    private Double tempoMedio;
    private Integer volumeTotal;
    private BigDecimal custoMedio;
	
    private Long cpfUsuario;
    private String nomeUsuario;
    private String emailUsuario;

}
