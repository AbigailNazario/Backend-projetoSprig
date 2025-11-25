package br.edu.pe.senac.pi_tads049.sprig.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Fornecedor;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.FornecedorRepository;

@Service
public class FornecedorService {

	 @Autowired
	    private FornecedorRepository fornecedorRepository;

	    // Cadastrar novo fornecedor
	    @Transactional
	    public Fornecedor cadastrarFornecedor(Fornecedor fornecedor) {
	        // Verificar se já existe fornecedor com este CNPJ
	        if (fornecedorRepository.existsByCnpj(fornecedor.getCnpj())) {
	            throw new RuntimeException("Já existe um fornecedor cadastrado com este CNPJ");
	        }
	        return fornecedorRepository.save(fornecedor);
	    }

	    // Listar todos os fornecedores
	    public List<Fornecedor> listarTodos() {
	        return fornecedorRepository.findAll();
	    }

	    // Buscar fornecedor por ID
	    public Optional<Fornecedor> buscarPorId(Integer id) {
	        return fornecedorRepository.findById(id);
	    }

	    // Buscar fornecedor por CNPJ
	    public Optional<Fornecedor> buscarPorCnpj(String cnpj) {
	        return fornecedorRepository.findByCnpj(cnpj);
	    }

	    // Atualizar fornecedor
	    @Transactional
	    public Fornecedor atualizarFornecedor(Integer id, Fornecedor fornecedorAtualizado) {
	        Fornecedor fornecedor = fornecedorRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

	        fornecedor.setNome(fornecedorAtualizado.getNome());
	        fornecedor.setCnpj(fornecedorAtualizado.getCnpj());
	        fornecedor.setTelefones(fornecedorAtualizado.getTelefones());
	        fornecedor.setEnderecos(fornecedorAtualizado.getEnderecos());

	        return fornecedorRepository.save(fornecedor);
	    }

	    // Deletar fornecedor
	    @Transactional
	    public void deletarFornecedor(Integer id) {
	        if (!fornecedorRepository.existsById(id)) {
	            throw new RuntimeException("Fornecedor não encontrado");
	        }
	        fornecedorRepository.deleteById(id);
	    }
	}
