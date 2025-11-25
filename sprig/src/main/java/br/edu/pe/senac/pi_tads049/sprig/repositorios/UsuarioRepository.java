package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
	boolean existsByEmail(String email);
    boolean existsByCpf(Long cpf);
    
	Optional<Usuario> findByCpf(Long cpf);

	void deleteByCpf(Long cpf);


	Usuario save(Usuario usuario);

	List<Usuario> findAll();
	
    
	
}
