package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.util.List;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long cpf;
    private String nome;
    private String email;
    private String regiaoAtuacao;
    private String perfil;
    private List<String> telefones;

}
