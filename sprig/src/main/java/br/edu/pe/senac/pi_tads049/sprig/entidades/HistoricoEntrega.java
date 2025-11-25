package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class HistoricoEntrega {
    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne
    private Entregas entrega;
    
    private LocalDateTime dataHora;
    private Status statusAnterior;
    private Status statusNovo;
    private String observacao;
    private String usuarioResponsavel;
}

