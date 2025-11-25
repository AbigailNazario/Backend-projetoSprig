package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Motorista;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Veiculo;
import br.edu.pe.senac.pi_tads049.sprig.entidades.VeiculoMotorista;

@Repository
public interface VeiculoMotoristaRepository extends JpaRepository<VeiculoMotorista, Integer> {
    
    // Buscar alocações por veículo
    List<VeiculoMotorista> findByVeiculo(Veiculo veiculo);
    
    // Buscar alocações por motorista
    List<VeiculoMotorista> findByMotorista(Motorista motorista);
    
    List<VeiculoMotorista> findByMotoristaIdMotorista(Integer idMotorista);
    
    List<VeiculoMotorista> findByVeiculoIdVeiculo(Integer idVeiculo);
    
    // Buscar alocação específica de veículo e motorista
    Optional<VeiculoMotorista> findByVeiculoAndMotorista(Veiculo veiculo, Motorista motorista);
}
