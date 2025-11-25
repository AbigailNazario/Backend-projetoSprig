package br.edu.pe.senac.pi_tads049.sprig.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.pe.senac.pi_tads049.sprig.dto.RelatorioDTO;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Entregas;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Estoque;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Motorista;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Rota;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EntregasRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EstoqueRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.MotoristaRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.RotaRepository;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço completo de geração de relatórios
 * Implementa cálculos reais e geração de PDF
 */
@Service
public class RelatorioCompletoService {

    @Autowired
    private EntregasRepository entregasRepository;
    
    @Autowired
    private RotaRepository rotaRepository;
    
    @Autowired
    private EstoqueRepository estoqueRepository;
    
    @Autowired
    private MotoristaRepository motoristaRepository;

    /**
     * Gera relatório de entregas com dados reais do banco
     */
    public RelatorioDTO gerarRelatorioEntregasReal(LocalDate dataInicio, LocalDate dataFim) {
        RelatorioDTO dto = new RelatorioDTO();
        dto.setTitulo("Relatório de Entregas por Período");
        dto.setDataInicio(dataInicio);
        dto.setDataFim(dataFim);
        dto.setDataGeracao(LocalDate.now());
        
        // Busca entregas no período
        List<Entregas> entregas = entregasRepository.findEntregasPorPeriodo(dataInicio, dataFim);
        
        // Total de entregas
        dto.setTotalEntregas(entregas.size());
        
        // Calcula tempo médio de entrega (em horas)
        double tempoMedio = entregas.stream()
            .filter(e -> e.getDataEntrega() != null && e.getDataPrevista() != null)
            .mapToLong(e -> ChronoUnit.HOURS.between(
                e.getDataPrevista().atStartOfDay(), 
                e.getDataEntrega().atStartOfDay()))
            .average()
            .orElse(0.0);
        dto.setTempoMedio(Math.round(tempoMedio * 100.0) / 100.0);
        
        // Volume total entregue
        int volumeTotal = entregas.stream()
            .mapToInt(Entregas::getQuantidadeEntregue)
            .sum();
        dto.setVolumeTotal(volumeTotal);
        
        // Custo médio por entrega
        BigDecimal custoTotal = entregas.stream()
            .filter(e -> e.getRota() != null)
            .map(e -> e.getRota().getCustoEstimado())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal custoMedio = entregas.size() > 0 
            ? custoTotal.divide(BigDecimal.valueOf(entregas.size()), 2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;
        dto.setCustoMedio(custoMedio);
        
        // Detalhes por status
        Map<String, Long> porStatus = entregas.stream()
            .collect(Collectors.groupingBy(
                e -> e.getStatus().toString(), 
                Collectors.counting()));
        
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("📊 RESUMO DO PERÍODO\n\n");
        detalhes.append(String.format("Total de Entregas: %d\n", dto.getTotalEntregas()));
        detalhes.append(String.format("Tempo Médio: %.2f horas\n", dto.getTempoMedio()));
        detalhes.append(String.format("Volume Total: %d unidades\n", dto.getVolumeTotal()));
        detalhes.append(String.format("Custo Médio: R$ %.2f\n\n", dto.getCustoMedio()));
        detalhes.append("📈 POR STATUS:\n");
        porStatus.forEach((status, count) -> 
            detalhes.append(String.format("  • %s: %d\n", status, count)));
        
        dto.setConteudoDetalhes(detalhes.toString());
        
        return dto;
    }

    /**
     * Gera relatório de desempenho de motoristas
     */
    public RelatorioDTO gerarRelatorioMotoristasReal(LocalDate dataInicio, LocalDate dataFim) {
        RelatorioDTO dto = new RelatorioDTO();
        dto.setTitulo("Relatório de Desempenho de Motoristas");
        dto.setDataInicio(dataInicio);
        dto.setDataFim(dataFim);
        dto.setDataGeracao(LocalDate.now());
        
        // Busca todas as rotas no período
        List<Rota> rotas = rotaRepository.findAll().stream()
            .filter(r -> !r.getDataSaida().isBefore(dataInicio) && 
                        !r.getDataSaida().isAfter(dataFim))
            .collect(Collectors.toList());
        
        // Agrupa por motorista
        Map<Motorista, List<Rota>> rotasPorMotorista = rotas.stream()
            .filter(r -> r.getMotorista() != null)
            .collect(Collectors.groupingBy(Rota::getMotorista));
        
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("👨‍✈️ DESEMPENHO POR MOTORISTA\n\n");
        
        int totalEntregas = 0;
        double distanciaTotal = 0;
        BigDecimal custoTotal = BigDecimal.ZERO;
        
        for (Map.Entry<Motorista, List<Rota>> entry : rotasPorMotorista.entrySet()) {
            Motorista motorista = entry.getKey();
            List<Rota> rotasMotorista = entry.getValue();
            
            int entregas = rotasMotorista.size();
            double distancia = rotasMotorista.stream()
                .mapToDouble(Rota::getDistancia)
                .sum();
            BigDecimal custo = rotasMotorista.stream()
                .map(Rota::getCustoEstimado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            totalEntregas += entregas;
            distanciaTotal += distancia;
            custoTotal = custoTotal.add(custo);
            
            detalhes.append(String.format("📦 %s (CNH: %s)\n", 
                motorista.getNome(), motorista.getCnh()));
            detalhes.append(String.format("   Entregas: %d\n", entregas));
            detalhes.append(String.format("   Distância: %.2f km\n", distancia));
            detalhes.append(String.format("   Custo: R$ %.2f\n\n", custo));
        }
        
        dto.setTotalEntregas(totalEntregas);
        dto.setVolumeTotal((int) distanciaTotal);
        dto.setCustoMedio(totalEntregas > 0 
            ? custoTotal.divide(BigDecimal.valueOf(totalEntregas), 2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO);
        dto.setTempoMedio(0.0); // Não aplicável para motoristas
        
        dto.setConteudoDetalhes(detalhes.toString());
        
        return dto;
    }

    /**
     * Gera relatório de estoque atual
     */
    public RelatorioDTO gerarRelatorioEstoqueReal() {
        RelatorioDTO dto = new RelatorioDTO();
        dto.setTitulo("Relatório de Estoque Atual");
        dto.setDataGeracao(LocalDate.now());
        dto.setDataInicio(LocalDate.now());
        dto.setDataFim(LocalDate.now());
        
        List<Estoque> estoques = estoqueRepository.findAll();
        
        // Calcula métricas
        int totalItens = estoques.size();
        int volumeTotal = estoques.stream()
            .mapToInt(Estoque::getQuantidadeAtual)
            .sum();
        
        List<Estoque> abaixoMinimo = estoqueRepository.findEstoquesAbaixoDoMinimo();
        List<Estoque> acimaMaximo = estoqueRepository.findEstoquesAcimaDoMaximo();
        
        dto.setTotalEntregas(totalItens);
        dto.setVolumeTotal(volumeTotal);
        dto.setTempoMedio(0.0);
        dto.setCustoMedio(BigDecimal.ZERO);
        
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("📦 SITUAÇÃO DO ESTOQUE\n\n");
        detalhes.append(String.format("Total de Itens: %d\n", totalItens));
        detalhes.append(String.format("Volume Total: %d unidades\n\n", volumeTotal));
        detalhes.append(String.format("⚠️ Abaixo do Mínimo: %d itens\n", abaixoMinimo.size()));
        detalhes.append(String.format("📈 Acima do Máximo: %d itens\n\n", acimaMaximo.size()));
        
        if (!abaixoMinimo.isEmpty()) {
            detalhes.append("🔴 ITENS CRÍTICOS (Abaixo do Mínimo):\n");
            abaixoMinimo.forEach(e -> 
                detalhes.append(String.format("  • Lote %d: %d/%d unidades\n", 
                    e.getLote().getIdLote(), 
                    e.getQuantidadeAtual(), 
                    e.getQuantidadeMinima())));
        }
        
        dto.setConteudoDetalhes(detalhes.toString());
        
        return dto;
    }

    /**
     * Gera PDF do relatório de entregas
     */
    public byte[] gerarPDFEntregas(LocalDate dataInicio, LocalDate dataFim) {
        try {
            RelatorioDTO dados = gerarRelatorioEntregasReal(dataInicio, dataFim);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Título
            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("SPRIG - Sistema de Logística de Sementes", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            document.add(new Paragraph(" "));
            
            Font subtituloFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Paragraph subtitulo = new Paragraph(dados.getTitulo(), subtituloFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);
            
            document.add(new Paragraph(" "));
            
            // Período
            Font normalFont = new Font(Font.HELVETICA, 10);
            document.add(new Paragraph(
                String.format("Período: %s a %s", dataInicio, dataFim), normalFont));
            document.add(new Paragraph(
                String.format("Data de Geração: %s", LocalDate.now()), normalFont));
            
            document.add(new Paragraph(" "));
            
            // Tabela de métricas
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            
            addCelula(table, "Métrica", true);
            addCelula(table, "Valor", true);
            
            addCelula(table, "Total de Entregas", false);
            addCelula(table, String.valueOf(dados.getTotalEntregas()), false);
            
            addCelula(table, "Tempo Médio (horas)", false);
            addCelula(table, String.format("%.2f", dados.getTempoMedio()), false);
            
            addCelula(table, "Volume Total", false);
            addCelula(table, String.valueOf(dados.getVolumeTotal()), false);
            
            addCelula(table, "Custo Médio", false);
            addCelula(table, String.format("R$ %.2f", dados.getCustoMedio()), false);
            
            document.add(table);
            
            document.add(new Paragraph(" "));
            
            // Detalhes
            document.add(new Paragraph("Detalhes:", subtituloFont));
            document.add(new Paragraph(dados.getConteudoDetalhes(), normalFont));
            
            document.close();
            
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
    
    private void addCelula(PdfPTable table, String texto, boolean header) {
        PdfPCell cell = new PdfPCell(new Phrase(texto));
        if (header) {
            cell.setBackgroundColor(new java.awt.Color(200, 200, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        table.addCell(cell);
    }
}