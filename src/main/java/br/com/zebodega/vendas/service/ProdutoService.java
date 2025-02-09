package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.ProdutoModel;
import br.com.zebodega.vendas.repository.ProdutoRepository;
import br.com.zebodega.vendas.rest.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public ProdutoDTO obterPorId(Long id) {
        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto com ID " + id + " não encontrado."));
        return produto.toDTO();
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> obterTodos() {
        List<ProdutoModel> listaProdutos = produtoRepository.findAll();
        return listaProdutos.stream()
                .map(ProdutoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoDTO salvar(ProdutoModel novoProduto) {
        try {
            // Verificar se já existe um produto com o mesmo nome, ignorando maiúsculas e minúsculas.
            if (produtoRepository.existsByNomeIgnoreCase(novoProduto.getNome())) {
                throw new ConstraintException("Já existe um produto cadastrado com esse nome: " + novoProduto.getNome() + "!");
            }

            return produtoRepository.save(novoProduto).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível criar um novo produto! " + novoProduto.getNome());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o produto " + novoProduto.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível criar um novo produto " + novoProduto.getNome() + ", pois violou uma regra de negócio.");
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao salvar o produto " + novoProduto.getNome(), e);
        }
    }

    @Transactional
    public ProdutoDTO atualizar(ProdutoModel produtoExistente) {
        try {
            // Caso o produto não seja encontrado
            if (!produtoRepository.existsByNomeIgnoreCase(produtoExistente.getNome())) {
                throw new ObjectNotFoundException("O produto " + produtoExistente.getNome() + " não foi encontrado!");
            }

            return produtoRepository.save(produtoExistente).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o produto! " + produtoExistente.getNome());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o produto " + produtoExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o produto " + produtoExistente.getNome() + ", pois violou uma regra de negócio.");
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao atualizar o produto " + produtoExistente.getNome(), e);
        }
    }

    @Transactional
    public void deletar(ProdutoModel produtoExistente) {
        try {
            // Caso o produto não seja encontrado
            if (!produtoRepository.existsByNomeIgnoreCase(produtoExistente.getNome())) {
                throw new ObjectNotFoundException("O produto " + produtoExistente.getNome() + " não foi encontrado!");
            }

            produtoRepository.delete(produtoExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o produto! " + produtoExistente.getNome());
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o produto " + produtoExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o produto " + produtoExistente.getNome() + ", pois violou uma regra de negócio.");
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao deletar o produto " + produtoExistente.getNome(), e);
        }
    }
}
