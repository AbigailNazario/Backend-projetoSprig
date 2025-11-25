package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Destino;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Entregas;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Status;

@Repository
public interface EntregasRepository extends JpaRepository<Entregas, Integer> {
    
    // Buscar entregas por status
    List<Entregas> findByStatus(Status status);
    
    // Buscar entregas por destino
    List<Entregas> findByDestino(Destino destino);
    
    // Buscar entregas por data de saída
    List<Entregas> findByRotaDataSaida(LocalDate dataSaida);
    
    // Buscar entregas entre datas
    @Query("SELECT e FROM Entregas e WHERE "
            // Verifica se a data de Saída da Rota está entre o período de busca do usuário
            + "e.rota.dataSaida BETWEEN :dataInicio AND :dataFim "
            // OU verifica se a data Prevista da Entrega está entre o período de busca do usuário
            + "OR e.dataPrevista BETWEEN :dataInicio AND :dataFim") 
       List<Entregas> findEntregasPorPeriodo(
           @Param("dataInicio") LocalDate dataInicio, 
           @Param("dataFim") LocalDate dataFim
       );
    
    // Buscar entregas em atraso
    List<Entregas> findByStatusOrderByRotaDataSaidaDesc(Status status);
    
    // ========== QUERIES CORRIGIDAS PARA DASHBOARD ==========
    
    /**
     * Conta entregas concluídas (status = Entregue)
     */
    @Query("SELECT COUNT(e) FROM Entregas e WHERE e.status = 'Entregue'")
    long countEntregasConcluidas();
    
    /**
     * Conta entregas em rota (status = Em_rota)
     */
    @Query("SELECT COUNT(e) FROM Entregas e WHERE e.status = 'Em_rota'")
    long countEntregasEmRota();
    
    /**
     * Conta entregas pendentes (status = Pendente)
     */
    @Query("SELECT COUNT(e) FROM Entregas e WHERE e.status = 'Pendente'")
    long countEntregasPendentes();

    /**
     * Calcula tempo médio de entrega em horas
     * Compara dataPrevista com dataEntrega
     */
    @Query("SELECT AVG(TIMESTAMPDIFF(HOUR, e.dataPrevista, e.dataEntrega)) " +
           "FROM Entregas e WHERE e.dataEntrega IS NOT NULL")
    Double avgTempoEntregaHoras();

    /**
     * Soma total de quantidade entregue
     */
    @Query("SELECT SUM(e.quantidadeEntregue) FROM Entregas e")
    Long sumQuantidadeEntregue();
}