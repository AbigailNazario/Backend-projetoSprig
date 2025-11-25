package br.edu.pe.senac.pi_tads049.sprig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private Long totalLotes;
    private Long entregasConcluidas;
    private Long entregasEmRota;
    private Long entregasPendentes;
    private Double percentualEntregue;
    private Double tempoMedioEntregaHoras;
    private Long volumeTotalEntregue;
    private Double custoTotalEstimado;
    private Double distanciaTotalPercorrida;
    private Double custoPorKm;
    private Long estoquesAbaixoDoMinimo;
    private Long estoquesAcimaDoMaximo;
    private Long totalVeiculos;
    private Long totalMotoristas;
    private Long veiculosEmRota;
}