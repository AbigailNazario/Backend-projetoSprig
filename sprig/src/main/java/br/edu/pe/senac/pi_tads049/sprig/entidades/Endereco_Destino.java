package br.edu.pe.senac.pi_tads049.sprig.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Endereco_Destino {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnderecoD;
    private String numero;
    private String rua;
    private String cep;
    private String bairro;      
    private String cidade;      
    private String estado;


    @ManyToOne
    @JoinColumn(name = "fk_destino")
    private Destino destino;


}
