package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "destino")
public class Destino {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDestino;
	
	@Column(nullable = false)
	private String nomeDestino;
	
	@Column(nullable = false)
	private BigDecimal latitude;
	
	@Column(nullable = false)
	private BigDecimal longitude;

	@OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco_Destino> enderecos;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Tipo tipo;
	
	// CORREÇÃO: Adicionar campo agricultor para rastreabilidade
	@ManyToOne
	@JoinColumn(name = "fk_agricultor")
	private Agricultor agricultor;
	
	//construtor

	public Destino() {
    }

	public Destino(int idDestino, String nomeDestino, BigDecimal latitude, BigDecimal longitude, 
	               List<Endereco_Destino> enderecos, Tipo tipo, Agricultor agricultor) {
		super();
		this.idDestino = idDestino;
		this.nomeDestino = nomeDestino;
		this.latitude = latitude;
		this.longitude = longitude;
		this.enderecos = enderecos;
		this.tipo = tipo;
		this.agricultor = agricultor;
	}



}