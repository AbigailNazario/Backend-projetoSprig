package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Lote;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Status2;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {
    
    // Buscar lotes por status
    List<Lote> findByStatus(Status2 status);
    
    // Buscar lotes disponíveis em estoque
    List<Lote> findByStatusOrderByValidadeAsc(Status2 status);
    
    // Buscar lotes com validade próxima (alertas)
    @Query("SELECT l FROM Lote l WHERE l.validade <= :dataLimite AND l.status = :status")
    List<Lote> findLotesProximosVencimento(LocalDate dataLimite, Status2 status);
    
    // Buscar lotes com quantidade disponível
    List<Lote> findByQuantidadeGreaterThan(int quantidade);
    
    @Query("SELECT COUNT(l) FROM Lote l")
    long countLotes();
}
