package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Rota;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Integer> {
    
    // Buscar rotas por destino
    List<Rota> findByDestino(Destino destino);
    
    // Buscar rotas ordenadas por distância
    List<Rota> findAllByOrderByDistanciaAsc();
    
    // Buscar rotas com distância menor ou igual a um valor
    List<Rota> findByDistanciaLessThanEqual(double distancia);
    
    // Buscar rotas com tempo estimado menor ou igual a um valor
    List<Rota> findByTempoEstimadoLessThanEqual(int tempoEstimado);
    
    /**
     * Soma total de custos estimados de todas as rotas
     */
    @Query("SELECT SUM(r.custoEstimado) FROM Rota r")
    Double sumCustoEstimado();

    /**
     * Soma total de distâncias percorridas
     * CORREÇÃO: Campo correto é 'distancia', não 'distanciaTotal'
     */
    @Query("SELECT SUM(r.distancia) FROM Rota r")
    Double sumDistanciaTotal();
    
    /**
     * Conta total de rotas cadastradas
     */
    @Query("SELECT COUNT(r) FROM Rota r")
    long countRotas();
}
