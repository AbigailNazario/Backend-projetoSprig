package br.edu.pe.senac.pi_tads049.sprig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprigApplication.class, args);
		System.out.println("Sprig iniciado com sucesso!");
	}

}

