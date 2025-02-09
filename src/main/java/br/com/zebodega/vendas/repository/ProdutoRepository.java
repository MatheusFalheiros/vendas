package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade ProdutoModel.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    /**
     * Verifica se existe um produto cadastrado com o nome especificado,
     * ignorando maiúsculas e minúsculas.
     *
     * @param nome o nome do produto a ser verificado.
     * @return {@code true} se existir um produto com o nome fornecido,
     *         {@code false} caso contrário.
     */
    boolean existsByNomeIgnoreCase(String nome);
}
