package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
	private Integer idFornecedor;
    private Long cnpj;
    private String nome;
    private String email;
    private List<TelefoneFDTO> telefones;
    private List<EnderecoFDTO> enderecos;
    
    @Data
	 @NoArgsConstructor
	 @AllArgsConstructor
	 public class EnderecoFDTO {
		  private Integer idEnderecoF;
	      private String numero;
	      private String rua;
	      private String cep;
}
	 @Data
	 @NoArgsConstructor
	 @AllArgsConstructor
	 public class TelefoneFDTO {
		  private Integer idTelefoneF;
	      private String numero;
}
	

}
	 
	 