package br.edu.pe.senac.pi_tads049.sprig.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroRequest {
    
    @NotNull(message = "CPF é obrigatório")
    @NotBlank(message = "CPF não pode estar em branco")
    @CPF(message = "CPF inválido")
    private String cpf;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    private String regiaoAtuacao;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;
    
    @NotNull(message = "Perfil é obrigatório")
    private Perfil perfil;
}