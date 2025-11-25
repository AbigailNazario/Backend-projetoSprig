package br.edu.pe.senac.pi_tads049.sprig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.pe.senac.pi_tads049.sprig.dto.DashboardDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Agricultor;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Entregas;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.AgricultorRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EntregasRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EstoqueRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.LoteRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.MotoristaRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.RotaRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.VeiculoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para cálculo de métricas do Dashboard
 * Agrega dados de múltiplos repositórios para fornecer visão geral do sistema
 */
@Service
public class DashboardService {
    
    @Autowired 
    private EntregasRepository entregaRepo;
    
    @Autowired 
    private LoteRepository loteRepo;
    
    @Autowired 
    private RotaRepository rotaRepo;
    
    @Autowired
    private EstoqueRepository estoqueRepo;
    
    @Autowired
    private VeiculoRepository veiculoRepo;
    
    @Autowired
    private MotoristaRepository motoristaRepo;
    
    @Autowired
    private AgricultorRepository agricultorRepo;

    /**
     * Calcula todas as métricas do dashboard
     * @return DashboardDTO com todas as métricas calculadas
     */
    public DashboardDTO getDashboardMetrics() {
        DashboardDTO dto = new DashboardDTO();
        
        // Métricas de Lotes
        dto.setTotalLotes(loteRepo.countLotes());
        
        // Métricas de Entregas
        long entregasConcluidas = entregaRepo.countEntregasConcluidas();
        long entregasEmRota = entregaRepo.countEntregasEmRota();
        long entregasPendentes = entregaRepo.countEntregasPendentes();
        
        dto.setEntregasConcluidas(entregasConcluidas);
        dto.setEntregasEmRota(entregasEmRota);
        dto.setEntregasPendentes(entregasPendentes);
        
        // Calcula percentual de entregas concluídas
        long totalEntregas = entregasConcluidas + entregasEmRota + entregasPendentes;
        double pctEntregue = totalEntregas > 0 
            ? (double) entregasConcluidas / totalEntregas * 100.0
            : 0.0;
        dto.setPercentualEntregue(Math.round(pctEntregue * 100.0) / 100.0); // 2 casas decimais
        
        // Métricas de Tempo
        Double tempoMedio = entregaRepo.avgTempoEntregaHoras();
        dto.setTempoMedioEntregaHoras(tempoMedio != null ? tempoMedio : 0.0);
        
        // Métricas de Volume
        Long volume = entregaRepo.sumQuantidadeEntregue();
        dto.setVolumeTotalEntregue(volume != null ? volume : 0L);
        
        // Métricas de Custo e Distância
        Double custoTotal = rotaRepo.sumCustoEstimado();
        Double distanciaTotal = rotaRepo.sumDistanciaTotal();
        
        dto.setCustoTotalEstimado(custoTotal != null ? custoTotal : 0.0);
        dto.setDistanciaTotalPercorrida(distanciaTotal != null ? distanciaTotal : 0.0);
        
        // Calcula custo por km
        Double custoPorKm = (custoTotal != null && distanciaTotal != null && distanciaTotal > 0)
            ? custoTotal / distanciaTotal
            : 0.0;
        dto.setCustoPorKm(Math.round(custoPorKm * 100.0) / 100.0); // 2 casas decimais
        
        // Métricas de Estoque
        dto.setEstoquesAbaixoDoMinimo((long) estoqueRepo.findEstoquesAbaixoDoMinimo().size());
        dto.setEstoquesAcimaDoMaximo((long) estoqueRepo.findEstoquesAcimaDoMaximo().size());
        
        // Métricas de Veículos e Motoristas
        dto.setTotalVeiculos((long) veiculoRepo.findAll().size());
        dto.setTotalMotoristas((long) motoristaRepo.findAll().size());
        
        // Veículos em rota = entregas em rota (assumindo 1 veículo por entrega)
        dto.setVeiculosEmRota(entregasEmRota);
        
        return dto;
    }
    
    /**
     * Retorna métricas filtradas para um agricultor específico
     * Mostra apenas informações relevantes para o agricultor (suas entregas)
     * @param emailAgricultor Email do agricultor para filtrar
     * @return DashboardDTO com métricas filtradas
     */
    public DashboardDTO getDashboardMetricsAgricultor(String emailAgricultor) {
        DashboardDTO dto = new DashboardDTO();
        
        // Busca o agricultor pelo email
        Agricultor agricultor = agricultorRepo.findByEmail(emailAgricultor)
            .orElseThrow(() -> new RuntimeException("Agricultor não encontrado com email: " + emailAgricultor));
        
        // Busca todas as entregas
        List<Entregas> todasEntregas = entregaRepo.findAll();
        
        // Filtra entregas do agricultor (destinos que pertencem ao agricultor)
        List<Entregas> entregasAgricultor = todasEntregas.stream()
            .filter(e -> e.getDestino() != null && 
                        e.getDestino().getAgricultor() != null &&
                        e.getDestino().getAgricultor().getCpf() == agricultor.getCpf())
            .collect(Collectors.toList());
        
        // Calcula métricas específicas do agricultor
        long entregasConcluidas = entregasAgricultor.stream()
            .filter(e -> "Entregue".equals(e.getStatus().toString()))
            .count();
            
        long entregasEmRota = entregasAgricultor.stream()
            .filter(e -> "Em_rota".equals(e.getStatus().toString()))
            .count();
            
        long entregasPendentes = entregasAgricultor.stream()
            .filter(e -> "Pendente".equals(e.getStatus().toString()))
            .count();
        
        dto.setEntregasConcluidas(entregasConcluidas);
        dto.setEntregasEmRota(entregasEmRota);
        dto.setEntregasPendentes(entregasPendentes);
        
        // Calcula percentual de entregas concluídas
        long totalEntregas = entregasConcluidas + entregasEmRota + entregasPendentes;
        double pctEntregue = totalEntregas > 0 
            ? (double) entregasConcluidas / totalEntregas * 100.0
            : 0.0;
        dto.setPercentualEntregue(Math.round(pctEntregue * 100.0) / 100.0);
        
        // Volume total recebido pelo agricultor
        long volumeTotal = entregasAgricultor.stream()
            .mapToLong(Entregas::getQuantidadeEntregue)
            .sum();
        dto.setVolumeTotalEntregue(volumeTotal);
        
        // Tempo médio de entrega para o agricultor
        double tempoMedio = entregasAgricultor.stream()
            .filter(e -> e.getDataEntrega() != null && e.getDataPrevista() != null)
            .mapToLong(e -> java.time.temporal.ChronoUnit.HOURS.between(
                e.getDataPrevista().atStartOfDay(), 
                e.getDataEntrega().atStartOfDay()))
            .average()
            .orElse(0.0);
        dto.setTempoMedioEntregaHoras(Math.round(tempoMedio * 100.0) / 100.0);
        
        // Total de lotes recebidos
        long totalLotes = entregasAgricultor.stream()
            .filter(e -> e.getLote() != null)
            .map(e -> e.getLote().getIdLote())
            .distinct()
            .count();
        dto.setTotalLotes(totalLotes);
        
        // Zera métricas não relevantes para agricultor
        dto.setCustoTotalEstimado(0.0);
        dto.setDistanciaTotalPercorrida(0.0);
        dto.setCustoPorKm(0.0);
        dto.setEstoquesAbaixoDoMinimo(0L);
        dto.setEstoquesAcimaDoMaximo(0L);
        dto.setTotalVeiculos(0L);
        dto.setTotalMotoristas(0L);
        dto.setVeiculosEmRota(0L);
        
        return dto;
    }
}