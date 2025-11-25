package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Motorista;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Integer> {
	
	Optional<Motorista> findByIdMotorista(int idMortorista);
    
    // Buscar motorista por CNH
    Optional<Motorista> findByCnh(String cnh);
    
    // Verificar se existe motorista com determinada CNH
    boolean existsByCnh(String cnh);
    
    // Buscar motorista por nome
    Optional<Motorista> findByNome(String nome);
}
