package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Relatorio;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Integer> {
    
    // Buscar relatórios por data de geração
    List<Relatorio> findByDataGeracao(LocalDate dataGeracao);
    
    // Buscar relatórios entre datas
    @Query("SELECT r FROM Relatorio r WHERE r.dataGeracao BETWEEN :dataInicio AND :dataFim")
    List<Relatorio> findRelatoriosPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
    
    // Buscar relatórios ordenados por data (mais recentes primeiro)
    List<Relatorio> findAllByOrderByDataGeracaoDesc();
}
