package br.edu.pe.senac.pi_tads049.sprig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private String perfil;
    private String nome;
    private String email;
    private Long expiracaoEm;
    
    // Construtor sem expiracaoEm para compatibilidade
    public LoginResponseDTO(String token, String tipo, String perfil, String nome, String email) {
        this.token = token;
        this.tipo = tipo;
        this.perfil = perfil;
        this.nome = nome;
        this.email = email;
    }
}