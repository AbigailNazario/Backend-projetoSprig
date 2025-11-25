package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Entregas")
public class Entregas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEntregas;
	
	@Column(nullable = false)
	private java.time.LocalDate dataPrevista;
	
	
	@Column
	private java.time.LocalDate dataEntrega;
	
	
	@Column(nullable = false)
	private int quantidadeEntregue;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;
	
	@ManyToOne
    @JoinColumn(name = "fk_lote", nullable = false)
	private Lote lote;
	
	@ManyToOne
    @JoinColumn(name = "fk_rota", nullable = false)
	private Rota rota;
	
	@ManyToOne
    @JoinColumn(name = "fk_destino", nullable = false)
	private Destino destino;
	
	
	//construtor

	public Entregas() {
    }


	public Entregas(int idEntregas, LocalDate dataPrevista, LocalDate dataEntrega, int quantidadeEntregue,
			Status status, Lote lote, Rota rota, Destino destino) {
		this.idEntregas = idEntregas;
		this.dataPrevista = dataPrevista;
		this.dataEntrega = dataEntrega;
		this.quantidadeEntregue = quantidadeEntregue;
		this.status = status;
		this.lote = lote;
		this.rota = rota;
		this.destino = destino;
	}



	
}
