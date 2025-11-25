package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "motorista")
public class Motorista {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMotorista;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cnh;
	
	@OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TelefoneMotorista> telefones;
	
	public Motorista() {
	}

	public Motorista(int idMotorista, String nome, String cnh) {
		this.idMotorista = idMotorista;
		this.nome = nome;
		this.cnh = cnh;
	}
	
	
}