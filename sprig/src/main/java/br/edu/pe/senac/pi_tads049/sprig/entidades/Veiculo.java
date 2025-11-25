package br.edu.pe.senac.pi_tads049.sprig.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "veiculo")
public class Veiculo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVeiculo;
	
	@Column(nullable = false)
	private String placa;
	
	@Column(nullable = false)
	private String modelo;
	
	@Column(nullable = false)
	private int capacidade;
	
	public Veiculo() {
	}

	public Veiculo(int idVeiculo, String placa, String modelo, int capacidade) {
		super();
		this.idVeiculo = idVeiculo;
		this.placa = placa;
		this.modelo = modelo;
		this.capacidade = capacidade;
	}

	
	
	
}
