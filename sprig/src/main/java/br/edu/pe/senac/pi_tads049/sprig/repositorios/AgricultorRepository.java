package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Agricultor;

@Repository
public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
    
    /**
     * Busca agricultor por email
     */
    Optional<Agricultor> findByEmail(String email);
    
    /**
     * Busca agricultor por CPF
     */
    Optional<Agricultor> findByCpf(Long cpf);
    
    /**
     * Busca agricultores por região de atuação
     */
    List<Agricultor> findByRegiaoAtuacao(String regiaoAtuacao);
    
    /**
     * Busca agricultores por nome (busca parcial, case insensitive)
     */
    List<Agricultor> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Verifica se existe agricultor com o email
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se existe agricultor com o CPF
     */
    boolean existsByCpf(Long cpf);
    
    /**
     * Lista todos os agricultores ordenados por nome
     */
    List<Agricultor> findAllByOrderByNomeAsc();
    
    /**
     * Busca agricultores com entregas pendentes
     */
    @Query("SELECT DISTINCT a FROM Agricultor a " +
           "JOIN Destino d ON d.agricultor = a " +
           "JOIN Entregas e ON e.destino = d " +
           "WHERE e.status = 'Em_rota'")
    List<Agricultor> findAgricultoresComEntregasPendentes();
}