package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Tipo;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Integer> {
    
    // Buscar destinos por tipo (Associação, Agricultor, Comunidade)
    List<Destino> findByTipo(Tipo tipo);
    
    // Buscar destino por nome
    List<Destino> findByNomeDestinoContainingIgnoreCase(String nomeDestino);

}
