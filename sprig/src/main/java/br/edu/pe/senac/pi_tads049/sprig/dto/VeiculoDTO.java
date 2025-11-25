package br.edu.pe.senac.pi_tads049.sprig.dto;

import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {
    private Integer idVeiculo;
    private String placa;
    private String modelo;
    private Integer capacidade;
    private String tipo;
}
