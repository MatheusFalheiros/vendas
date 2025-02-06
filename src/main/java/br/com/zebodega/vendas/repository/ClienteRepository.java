package br.com.zebodega.vendas.repository;

import br.com.zebodega.vendas.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por gerenciar as operações de persistência
 * relacionadas à entidade ClienteModel.
 */

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    /**
     * Verifica se existe um cliente cadastrado com o CPF especificado.
     *
     * @param cpf O CPF a ser verificado.
     * @return {@code true} se existir um garçom com o CPF fornecido, {@code false} caso contrário.
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe um cliente cadastrado com o e-mail especificado.
     *
     * @param email O e-mail a ser verificado.
     * @return {@code true} se existir um cliente com o e-mail fornecido, {@code false} caso contrário.
     */
    boolean existsByEmail(String email);

}
