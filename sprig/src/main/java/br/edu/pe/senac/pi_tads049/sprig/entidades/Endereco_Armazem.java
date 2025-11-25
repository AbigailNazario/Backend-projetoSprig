package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Endereco_Armazem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEnderecoA;
    private String numero;
    private String rua;
    private String cep;
    private String logradouro;  
    private String bairro;      
    private String cidade;      
    private String estado;
    
    


    public int getIdEnderecoA() {
		return idEnderecoA;
	}




	public void setIdEnderecoA(int idEnderecoA) {
		this.idEnderecoA = idEnderecoA;
	}




	public String getNumero() {
		return numero;
	}




	public void setNumero(String numero) {
		this.numero = numero;
	}




	public String getRua() {
		return rua;
	}




	public void setRua(String rua) {
		this.rua = rua;
	}




	public String getCep() {
		return cep;
	}




	public void setCep(String cep) {
		this.cep = cep;
	}




	public String getLogradouro() {
		return logradouro;
	}




	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}




	public String getBairro() {
		return bairro;
	}




	public void setBairro(String bairro) {
		this.bairro = bairro;
	}




	public String getCidade() {
		return cidade;
	}




	public void setCidade(String cidade) {
		this.cidade = cidade;
	}




	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	public Armazem getArmazem() {
		return armazem;
	}




	public void setArmazem(Armazem armazem) {
		this.armazem = armazem;
	}




	@ManyToOne
    @JoinColumn(name = "fk_armazem")
    private Armazem armazem;




}
