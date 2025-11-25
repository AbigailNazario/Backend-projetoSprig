package br.edu.pe.senac.pi_tads049.sprig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArmazemDTO {
    private Integer idArmazem;
    private String nome;
    private Integer capacidadeTotal;
    private Integer capacidadeOcupada;
    private Double percentualOcupacao;
    
    // Informações do Responsável
    private Long responsavelCpf;
    private String responsavelNome;
    private String responsavelEmail;
    
    // Endereços
    private List<EnderecoADTO> enderecos;
    
    // Estatísticas
    private Integer totalLotes;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnderecoADTO {
    	private Integer idEnderecoA;
        private String numero;
        private String rua;
        private String cep;
        private String logradouro; 
        private String bairro;     
        private String cidade;   
        private String estado;
    }
}