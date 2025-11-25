package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "armazem")
public class Armazem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idArmazem;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)//atributo composto
	private int capacidadeTotal;
	
	@OneToMany(mappedBy = "armazem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco_Armazem> enderecos;
	
	@ManyToOne
	@JoinColumn(name = "fk_responsavel", nullable = false)
	private Usuario responsavel;
	
	
	public int getIdArmazem() {
		return idArmazem;
	}

	public void setIdArmazem(int idArmazem) {
		this.idArmazem = idArmazem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCapacidadeTotal() {
		return capacidadeTotal;
	}

	public void setCapacidadeTotal(int capacidadeTotal) {
		this.capacidadeTotal = capacidadeTotal;
	}

	public List<Endereco_Armazem> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco_Armazem> enderecos) {
		this.enderecos = enderecos;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public Armazem() {
	}
	
	public Armazem(int idArmazem, String nome, int capacidadeTotal, List<Endereco_Armazem> enderecos, Usuario responsavel) {
		this.idArmazem = idArmazem;
		this.nome = nome;
		this.capacidadeTotal = capacidadeTotal;
		this.enderecos = enderecos;
		this.responsavel = responsavel;
	}



}