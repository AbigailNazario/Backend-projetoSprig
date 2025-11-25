package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
public class Usuario {
	@Id
	private Long cpf;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)//atributo composto
	private String regiaoAtuacao;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TelefoneUsuario> telefones;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Perfil perfil;
	
	@Column(nullable = false)
	private String senha;
	
	//get e set

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRegiaoAtuacao() {
		return regiaoAtuacao;
	}

	public void setRegiaoAtuacao(String regiaoAtuacao)  {
		this.regiaoAtuacao = regiaoAtuacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}

	//construtor

	public Usuario() {
    }

	public Usuario(String nome, Long cpf, String regiaoAtuacao, String email, String senha, Perfil perfil) {
        this.nome = nome;
        this.cpf = cpf;
        this.regiaoAtuacao = regiaoAtuacao;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }
}