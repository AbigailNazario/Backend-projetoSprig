package br.edu.pe.senac.pi_tads049.sprig.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.pe.senac.pi_tads049.sprig.dto.CadastroRequest;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Agricultor;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Gestor;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Tecnico;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Usuario;
import br.edu.pe.senac.pi_tads049.sprig.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody CadastroRequest request) {
        try {
            // Remove non-numeric characters from CPF and convert to Long
            String cpfClean = request.getCpf().replaceAll("\\D", "");
            Long cpf = Long.parseLong(cpfClean);
            
            Usuario usuario;
            
            // Cria a instância correta baseada no perfil
            switch (request.getPerfil()) {
                case GESTOR:
                    usuario = new Gestor(request.getNome(), cpf, 
                                        request.getRegiaoAtuacao(), request.getEmail(), 
                                        request.getSenha());
                    break;
                case TECNICO:
                    usuario = new Tecnico(request.getNome(), cpf, 
                                         request.getRegiaoAtuacao(), request.getEmail(), 
                                         request.getSenha());
                    break;
                case AGRICULTOR:
                    usuario = new Agricultor(request.getNome(), cpf, 
                                            request.getRegiaoAtuacao(), request.getEmail(), 
                                            request.getSenha());
                    break;
                default:
                    return ResponseEntity.badRequest().body("Perfil inválido. Use: GESTOR, TECNICO ou AGRICULTOR");
            }
            
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok("Usuário cadastrado com sucesso: " + novoUsuario.getCpf());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("CPF deve conter apenas números");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}