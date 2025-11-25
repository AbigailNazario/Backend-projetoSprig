package br.edu.pe.senac.pi_tads049.sprig.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.pe.senac.pi_tads049.sprig.dto.DashboardDTO;
import br.edu.pe.senac.pi_tads049.sprig.service.DashboardService;

@RestController
@RequestMapping("/api")
public class DashboardController {
    @Autowired private DashboardService dashboardService;

    @GetMapping("/dashboardG")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<DashboardDTO> getDashboardG() {
        return ResponseEntity.ok(dashboardService.getDashboardMetrics());
    }
    
    @GetMapping("/dashboardT")
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<DashboardDTO> getDashboardT() {
        return ResponseEntity.ok(dashboardService.getDashboardMetrics());
    }
    
    @GetMapping("/dashboardA")
    @PreAuthorize("hasRole('AGRICULTOR')")
    public ResponseEntity<DashboardDTO> getDashboardA() {
        return ResponseEntity.ok(dashboardService.getDashboardMetrics());
    }
}

