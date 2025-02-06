package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade PedidoModel.
 */
@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    /**
     * Verifica se existe um pedido cadastrado com o numero de pedido especificado.
     *
     * @param numeroPedido  a ser verificado.
     * @return {@code true} se existir numeroPedido fornecido, {@code false} caso contrário.
     */
    boolean existsByNumeroPedido(String numeroPedido);

    /**
     * Busca pedidos dentro de um intervalo de datas e com status específico.
     *
     * @param dataInicial A data inicial para a busca.
     * @param dataFinal A data final para a busca.
     * @param status O status dos pedidos a serem encontrados.
     * @return Lista de pedidos que atendem os critérios fornecidos.
     */
    List<PedidoModel> findByDataCriacaoBetweenAndStatus(LocalDate dataInicial, LocalDate dataFinal, String status);

}
