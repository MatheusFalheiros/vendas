package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade FormaPagmentoModel.
 */

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Long> {

    /**
     * Verifica se existe uma forma de pagamento cadastrado com o idFormaPagamento especificado.
     *
     * @param idFormaPagamento a ser verificado.
     * @return {@code true} se existir idFormaPagamento fornecido, {@code false} caso contrário.
     */
    boolean existsByIdFormaPagamento(Long idFormaPagamento);
}
