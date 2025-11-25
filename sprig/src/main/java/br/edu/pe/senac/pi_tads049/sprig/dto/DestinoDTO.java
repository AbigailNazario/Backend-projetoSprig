package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.math.BigDecimal;
import java.util.List;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Tipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinoDTO {
    private Integer idDestino;
    private String nomeDestino;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String tipo;
    private List<EnderecoDDTO> enderecos;
	
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class EnderecoDDTO {
    	private Integer idEnderecoD;
        private String numero;
        private String rua;
        private String cep;
    }
	
}
