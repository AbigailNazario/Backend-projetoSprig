package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agricultor")
public class Agricultor extends Usuario {
	
	private String propriedade;
    
    public Agricultor() {
        super();
    }

    public Agricultor(String nome, Long cpf, String regiaoAtuacao, String email, String senha) {
        super(nome, cpf, regiaoAtuacao, email, senha, Perfil.AGRICULTOR);
    }

	
}