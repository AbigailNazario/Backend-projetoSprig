package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Armazem;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Usuario;

@Repository
public interface ArmazemRepository extends JpaRepository<Armazem, Integer> {
    
    // Buscar armazém por nome
    Optional<Armazem> findByNome(String nome);
    
    // Buscar armazéns por responsável
    List<Armazem> findByResponsavel(Usuario responsavel);
    
    // Buscar armazéns com capacidade maior ou igual a um valor
    List<Armazem> findByCapacidadeTotalGreaterThanEqual(int capacidade);
}
