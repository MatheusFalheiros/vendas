package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade UsuarioeModel.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    /**
     * Verifica se existe um usuario cadastrado com o idCliente especificado.
     *
     * @param cliente a ser verificado.
     * @return {@code true} se existir idcliente fornecido, {@code false} caso contrário.
     */
    boolean existsByCliente(ClienteModel cliente);
}
