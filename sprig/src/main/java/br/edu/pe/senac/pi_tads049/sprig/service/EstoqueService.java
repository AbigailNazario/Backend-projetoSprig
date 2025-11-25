package br.edu.pe.senac.pi_tads049.sprig.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.pe.senac.pi_tads049.sprig.entidades.Armazem;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Estoque;
import br.edu.pe.senac.pi_tads049.sprig.entidades.Lote;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.ArmazemRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.EstoqueRepository;
import br.edu.pe.senac.pi_tads049.sprig.repositorios.LoteRepository;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;
    
    @Autowired
    private ArmazemRepository armazemRepository;
    
    @Autowired
    private LoteRepository loteRepository;

    /**
     * Cria novo registro de estoque
     */
    @Transactional
    public Estoque criarEstoque(Estoque estoque) {
        estoque.setUltimaAtualizacao(LocalDate.now());
        return estoqueRepository.save(estoque);
    }

    /**
     * Lista todos os estoques
     */
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    /**
     * Busca estoque por ID
     */
    public Optional<Estoque> buscarPorId(Integer id) {
        return estoqueRepository.findById(id);
    }

    /**
     * Lista estoques por armazém
     */
    public List<Estoque> listarPorArmazem(Integer armazemId) {
        Armazem armazem = armazemRepository.findById(armazemId)
            .orElseThrow(() -> new RuntimeException("Armazém não encontrado"));
        return estoqueRepository.findByArmazem(armazem);
    }

    /**
     * Busca estoque por lote
     */
    public Optional<Estoque> buscarPorLote(Integer loteId) {
        Lote lote = loteRepository.findById(loteId)
            .orElseThrow(() -> new RuntimeException("Lote não encontrado"));
        return estoqueRepository.findByLote(lote);
    }

    /**
     * Lista estoques abaixo do mínimo (alerta)
     */
    public List<Estoque> listarEstoquesAbaixoDoMinimo() {
        return estoqueRepository.findEstoquesAbaixoDoMinimo();
    }

    /**
     * Lista estoques acima do máximo (alerta)
     */
    public List<Estoque> listarEstoquesAcimaDoMaximo() {
        return estoqueRepository.findEstoquesAcimaDoMaximo();
    }

    /**
     * Atualiza quantidade de estoque
     */
    @Transactional
    public Estoque atualizarQuantidade(Integer id, Integer novaQuantidade) {
        Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }
        
        estoque.setQuantidadeAtual(novaQuantidade);
        estoque.setUltimaAtualizacao(LocalDate.now());
        
        return estoqueRepository.save(estoque);
    }

    /**
     * Adiciona quantidade ao estoque
     */
    @Transactional
    public Estoque adicionarQuantidade(Integer id, Integer quantidade) {
        Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        
        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade deve ser positiva");
        }
        
        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + quantidade);
        estoque.setUltimaAtualizacao(LocalDate.now());
        
        return estoqueRepository.save(estoque);
    }

    /**
     * Remove quantidade do estoque
     */
    @Transactional
    public Estoque removerQuantidade(Integer id, Integer quantidade) {
        Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        
        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade deve ser positiva");
        }
        
        int novaQuantidade = estoque.getQuantidadeAtual() - quantidade;
        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade insuficiente em estoque");
        }
        
        estoque.setQuantidadeAtual(novaQuantidade);
        estoque.setUltimaAtualizacao(LocalDate.now());
        
        return estoqueRepository.save(estoque);
    }

    /**
     * Atualiza limites de estoque
     */
    @Transactional
    public Estoque atualizarLimites(Integer id, Integer minimo, Integer maximo) {
        Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        
        if (minimo < 0 || maximo < 0) {
            throw new RuntimeException("Limites não podem ser negativos");
        }
        
        if (minimo > maximo) {
            throw new RuntimeException("Mínimo não pode ser maior que máximo");
        }
        
        estoque.setQuantidadeMinima(minimo);
        estoque.setQuantidadeMaxima(maximo);
        estoque.setUltimaAtualizacao(LocalDate.now());
        
        return estoqueRepository.save(estoque);
    }

    /**
     * Deleta registro de estoque
     */
    @Transactional
    public void deletarEstoque(Integer id) {
        if (!estoqueRepository.existsById(id)) {
            throw new RuntimeException("Estoque não encontrado");
        }
        estoqueRepository.deleteById(id);
    }

    /**
     * Verifica status do estoque (normal, baixo, alto)
     */
    public String verificarStatus(Integer id) {
        Estoque estoque = estoqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
        
        if (estoque.isAbaixoDoMinimo()) {
            return "BAIXO - Reabastecer urgentemente";
        } else if (estoque.isAcimaDoMaximo()) {
            return "ALTO - Excesso de estoque";
        } else {
            return "NORMAL - Dentro dos limites";
        }
    }
}