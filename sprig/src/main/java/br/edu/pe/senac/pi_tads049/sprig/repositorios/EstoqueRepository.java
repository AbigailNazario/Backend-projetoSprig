package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Armazem;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Estoque;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Lote;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    
    /**
     * Busca estoque por armazém
     */
    List<Estoque> findByArmazem(Armazem armazem);
    
    /**
     * Busca estoque por lote
     */
    Optional<Estoque> findByLote(Lote lote);
    
    /**
     * Lista estoques abaixo do mínimo
     */
    @Query("SELECT e FROM Estoque e WHERE e.quantidadeAtual < e.quantidadeMinima")
    List<Estoque> findEstoquesAbaixoDoMinimo();
    
    /**
     * Lista estoques acima do máximo
     */
    @Query("SELECT e FROM Estoque e WHERE e.quantidadeAtual > e.quantidadeMaxima")
    List<Estoque> findEstoquesAcimaDoMaximo();
    
    /**
     * Lista estoques por armazém ordenados por quantidade
     */
    List<Estoque> findByArmazemOrderByQuantidadeAtualDesc(Armazem armazem);
    
    /**
     * Verifica se existe estoque para um lote específico
     */
    boolean existsByLote(Lote lote);
}