package br.edu.pe.senac.pi_tads049.sprig.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    
    // Buscar fornecedor por CNPJ
    Optional<Fornecedor> findByCnpj(String cnpj);
    
    // Verificar se existe fornecedor com determinado CNPJ
    boolean existsByCnpj(String cnpj);
    
    // Buscar fornecedor por nome
    Optional<Fornecedor> findByNome(String nome);
}
