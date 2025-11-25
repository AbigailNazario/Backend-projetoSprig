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
public class TelefoneUsuario {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int idTelefoneU;

	    private String numero;

	    @ManyToOne
	    @JoinColumn(name = "fk_usuario")
	    private Usuario usuario;

}
