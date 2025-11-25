package br.edu.pe.senac.pi_tads049.sprig.entidades;

import java.time.Instant;
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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "lote")
public class Lote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLote;
	
	@Column(nullable = false)
	private int numeroLote;
	
	@Column(nullable = false)
	private String especie;
	
	@Column(nullable = false)
	private int quantidade;
	
	@Column(nullable = false)
	private java.time.Instant validade;
	
	@Column
	private java.time.LocalDate dataRecebimento;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status2 status;
	
	@Column
	private String qrCode;
	
	@ManyToOne
    @JoinColumn(name = "fk_armazem", nullable = false)
    private Armazem armazem;

    @ManyToOne
    @JoinColumn(name = "fk_fornecedor", nullable = false)
    private Fornecedor fornecedor;
	

	public Lote() {
    }


	public Lote(int idLote, int numeroLote, String especie, int quantidade, Instant validade, LocalDate dataRecebimento,
			Status2 status, String qrCode, Armazem armazem, Fornecedor fornecedor) {
		super();
		this.idLote = idLote;
		this.numeroLote = numeroLote;
		this.especie = especie;
		this.quantidade = quantidade;
		this.validade = validade;
		this.dataRecebimento = dataRecebimento;
		this.status = status;
		this.qrCode = qrCode;
		this.armazem = armazem;
		this.fornecedor = fornecedor;
	}

	
}
