package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Relatorio")
public class Relatorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRelatorio;
	
	@Column(nullable = false)
	private java.time.Instant periodoInicio;
	
	@Column(nullable = false)
	private java.time.Instant periodoFim;
	
	@Column(nullable = false)
	private int totalEntregas;
	
	@Column(nullable = false)
	private java.math.BigDecimal tempoMedio;
	
	@Column(nullable = false)
	private java.math.BigDecimal volumeTotal;
	
	@Column(nullable = false)
	private java.math.BigDecimal custoMedio;
	
	@Column(nullable = false)
	private java.time.LocalDate dataGeracao;
	
	@ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

	public Relatorio() {
    }

	public Relatorio(int idRelatorio, Instant periodoInicio, Instant periodoFim, int totalEntregas,
			BigDecimal tempoMedio, BigDecimal volumeTotal, BigDecimal custoMedio, LocalDate dataGeracao) {
		super();
		this.idRelatorio = idRelatorio;
		this.periodoInicio = periodoInicio;
		this.periodoFim = periodoFim;
		this.totalEntregas = totalEntregas;
		this.tempoMedio = tempoMedio;
		this.volumeTotal = volumeTotal;
		this.dataGeracao = dataGeracao;
		this.custoMedio = custoMedio;
	}

	
}