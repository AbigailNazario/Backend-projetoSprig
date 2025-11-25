package br.edu.pe.senac.pi_tads049.sprig.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.pe.senac.pi_tads049.sprig.dto.RelatorioDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    
    @Autowired
    private RelatorioService relatorioService;
    
    /**
     * Lista todos os relatórios
     * Acesso: GESTOR
     */
    @GetMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<List<RelatorioDTO>> listar() {
        return ResponseEntity.ok(relatorioService.listarTodos());
    }
    
    /**
     * Gera um novo relatório
     * Acesso: GESTOR
     */
    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<RelatorioDTO> gerar(@RequestBody RelatorioDTO dto) {
        return ResponseEntity.ok(relatorioService.gerarRelatorio(dto));
    }
    
    /**
     * Gera relatório de entregas por período
     * Acesso: GESTOR, TECNICO
     * FUNCIONALIDADE IMPLEMENTADA: Cálculos reais do banco
     */
    @GetMapping("/entregas")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<RelatorioDTO> gerarRelatorioEntregas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        RelatorioDTO relatorio = relatorioService.gerarRelatorioEntregas(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }
    
    /**
     * Gera relatório de desempenho de motoristas
     * Acesso: GESTOR, TECNICO
     * FUNCIONALIDADE IMPLEMENTADA: Cálculos reais do banco
     */
    @GetMapping("/motoristas")
    @PreAuthorize("hasAnyRole('GESTOR', 'TECNICO')")
    public ResponseEntity<RelatorioDTO> gerarRelatorioMotoristas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        RelatorioDTO relatorio = relatorioService.gerarRelatorioMotoristas(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }
    
    /**
     * Gera relatório de estoque atual
     * Acesso: GESTOR
     * FUNCIONALIDADE IMPLEMENTADA: Dados reais do estoque
     */
    @GetMapping("/estoque")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<RelatorioDTO> gerarRelatorioEstoque() {
        RelatorioDTO relatorio = relatorioService.gerarRelatorioEstoque();
        return ResponseEntity.ok(relatorio);
    }
    
    /**
     * Gera PDF do relatório de entregas
     * Acesso: GESTOR
     * FUNCIONALIDADE IMPLEMENTADA: Geração real de PDF com iText
     */
    @GetMapping("/entregas/pdf")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<byte[]> gerarPDFEntregas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        byte[] pdfBytes = relatorioService.gerarPDFEntregas(dataInicio, dataFim);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", 
            String.format("relatorio-entregas-%s-%s.pdf", dataInicio, dataFim));
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}