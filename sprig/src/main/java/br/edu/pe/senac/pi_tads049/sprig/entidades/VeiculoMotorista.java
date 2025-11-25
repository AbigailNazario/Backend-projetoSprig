package br.edu.pe.senac.pi_tads049.sprig.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculo_motorista")
@Getter @Setter
public class VeiculoMotorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeiculoMotorista;

    @ManyToOne
    @JoinColumn(name = "fk_motorista", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo", nullable = false)
    private Veiculo veiculo;
}
