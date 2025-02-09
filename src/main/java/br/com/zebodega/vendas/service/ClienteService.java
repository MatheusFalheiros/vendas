package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.repository.ClienteRepository;
import br.com.zebodega.vendas.rest.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    /**
     * Instância do repositório de clientes, responsável por realizar operações de
     * persistência e consulta diretamente no banco de dados.
     */

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtém um cliente pelo ID.
     *
     * @param id ID do cliente.
     * @return ClienteDTO representando o cliente encontrado.
     * @throws ObjectNotFoundException Se o cliente não for encontrado.
     */

    @Transactional(readOnly = true)
    public ClienteDTO obterPorId(Long id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente com ID " + id + " não encontrado."));
        return cliente.toDTO();
    }
    /**
            * Obtém a lista de todos os clientes cadastrados.
            *
            * @return Lista de ClienteDTO representando os clientes cadastrados.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> obterTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteModel::toDTO)
                .collect(Collectors.toList());
    }
    /**
     * Salva um novo cliente na base de dados.
     *
     * @param novoCliente ClienteModel contendo os dados do novo cliente.
     * @return ClienteDTO representando o cliente salvo.
     * @throws ConstraintException    Se o CPF ou e-mail já existirem.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha de conexão com o banco de dados.
     */

    @Transactional
    public ClienteDTO salvar(ClienteModel novoCliente) {
        try {
            //Caso ocorra uma tentativa de salvar um novo cliente com um cpf já existente.
            if (clienteRepository.existsByCpf(novoCliente.getCpf())) {
                throw new ConstraintException("Já existe um cliente cadastrado com esse CPF: " + novoCliente.getCpf() + " na base de dados!");
            }

            //Caso ocorra uma tentativa de salvar um novo cliente com um e-mail já existente.
            if (clienteRepository.existsByEmail(novoCliente.getEmail())) {
                throw new ConstraintException("Já existe um cliente com esse E-MAIL " + novoCliente.getEmail() + " na base de dados!");
            }

            //Salva o novo cliente na base de dados.
            return clienteRepository.save(novoCliente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o cliente " + novoCliente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o cliente " + novoCliente.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o cliente " + novoCliente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o cliente " + novoCliente.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }
    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param clienteExistente ClienteModel contendo os dados atualizados do cliente.
     * @return ClienteDTO representando o cliente atualizado.
     * @throws ConstraintException    Se o CPF não existir.
     * @throws DataIntegrityException Se ocorrer violação de integridade.
     * @throws BusinessRuleException  Se houver violação de regra de negócio.
     * @throws SQLException           Se ocorrer falha de conexão com o banco de dados.
     */

    @Transactional
    public ClienteDTO atualizar(ClienteModel clienteExistente) {
        try {
            //Caso ocorra uma tentativa de salvar um cliente que não existe utilizando um cpf.
            if (!clienteRepository.existsByCpf(clienteExistente.getCpf())) {
                throw new ConstraintException("O cliente com esse CPF " + clienteExistente.getCpf() + " não existe na base de dados!");
            }
            //Atualiza o cliente na base de dados.
            return clienteRepository.save(clienteExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o cliente " + clienteExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o cliente " + clienteExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o cliente " + clienteExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o cliente " + clienteExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o cliente" + clienteExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(ClienteModel clienteExistente) {
        try {
            //Caso ocorra uma tentativa de deletar um cliente que não existe utilizando um cpf.
            if (!clienteRepository.existsByCpf(clienteExistente.getCpf())) {
                throw new ConstraintException("O cliente com esse CPF " + clienteExistente.getCpf() + " não existe na base de dados!");
            }

            //Deletar o cliente na base de dados.
            clienteRepository.delete(clienteExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o cliente " + clienteExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o cliente " + clienteExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o cliente " + clienteExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + clienteExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o cliente" + clienteExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
}