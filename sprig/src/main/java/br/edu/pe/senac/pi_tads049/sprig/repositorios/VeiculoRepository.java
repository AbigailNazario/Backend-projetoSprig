package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
    
    // Buscar veículo por placa
    Optional<Veiculo> findByPlaca(String placa);
    
    // Verificar se existe veículo com determinada placa
    boolean existsByPlaca(String placa);
    
    // Buscar veículos por modelo
    List<Veiculo> findByModelo(String modelo);
    
    // Buscar veículos com capacidade maior ou igual a um valor
    List<Veiculo> findByCapacidadeGreaterThanEqual(int capacidade);
}
