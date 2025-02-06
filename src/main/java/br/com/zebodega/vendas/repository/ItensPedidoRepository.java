package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ItensPedidoModel;
import br.com.zebodega.vendas.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade ItensPedidoModel.
 */
@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedidoModel, Long> {

    /**
     * Verifica se existe um itensPedido cadastrado com o idPedido especificado.
     *
     * @param pedido a ser verificado.
     * @return {@code true} se existir Idpedido fornecido, {@code false} caso contrário.
     */
    boolean existsByPedido(PedidoModel pedido);

}
