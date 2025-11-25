package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.math.BigDecimal;
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
@Table(name = "rota")
public class Rota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRota;
	
	@Column(nullable = false)
	private java.time.LocalDate dataSaida;
	
	@Column
	private java.time.LocalDate dataRetorno;
	
	@Column(nullable = false)
	private double distancia;
	
	@Column(nullable = false)
	private double tempoEstimado;
	
	@Column(nullable = false)
	private java.math.BigDecimal custoEstimado;
	
	@ManyToOne
    @JoinColumn(name = "fk_motorista", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "fk_armazemOrigem", nullable = false)
    private Armazem armazemOrigem;
    
    @ManyToOne
    @JoinColumn(name = "fk_destino", nullable = false)
    private Destino destino;
    
	//construtor

	public Rota() {
    }

	public Rota(int idRota, LocalDate dataSaida, LocalDate dataRetorno, double distancia, double tempoEstimado,
			BigDecimal custoEstimado, Motorista motorista, Armazem armazemOrigem, Destino destino) {
		this.idRota = idRota;
		this.dataSaida = dataSaida;
		this.dataRetorno = dataRetorno;
		this.distancia = distancia;
		this.tempoEstimado = tempoEstimado;
		this.custoEstimado = custoEstimado;
		this.motorista = motorista;
		this.armazemOrigem = armazemOrigem;
		this.destino = destino;
	}

	
}