package br.edu.pe.senac.pi_tads049.sprig.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gestor")
public class Gestor extends Usuario {
	private String setorResponsavel;
    
    public Gestor() {
        super();
    }

    public Gestor(String nome, Long cpf, String regiaoAtuacao, String email, String senha) {
        super(nome, cpf, regiaoAtuacao, email, senha, Perfil.GESTOR);
    }
}