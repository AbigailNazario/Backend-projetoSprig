package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.util.List;

import br.edu.pe.senac.pi_tads049.sprig.entidades.TelefoneMotorista;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaDTO {
    private Integer idMotorista;
    private String nome;
    private String cnh;
    private List<String> telefones;

	@Data
	 @NoArgsConstructor
	 @AllArgsConstructor
	 public static class TelefoneMDTO {
		  private Integer idTelefoneM;
	      private String numero;
}
}
