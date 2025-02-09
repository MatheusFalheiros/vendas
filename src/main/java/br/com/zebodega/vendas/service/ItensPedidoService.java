package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.ItensPedidoModel;
import br.com.zebodega.vendas.repository.ItensPedidoRepository;
import br.com.zebodega.vendas.rest.ItensPedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItensPedidoService {

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    @Transactional(readOnly = true)
    public ItensPedidoDTO obterPorId(Long id) {
        ItensPedidoModel itemPedido = itensPedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item do pedido com ID " + id + " não encontrado."));
        return itemPedido.toDTO();
    }

    @Transactional(readOnly = true)
    public List<ItensPedidoDTO> obterTodos() {
        List<ItensPedidoModel> listaItensPedido = itensPedidoRepository.findAll();
        return listaItensPedido.stream()
                .map(ItensPedidoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItensPedidoDTO salvar(ItensPedidoModel novoItensPedido) {
        try {
            // Verifica se já existe um item de pedido com o mesmo ID
            if (itensPedidoRepository.existsByPedido(novoItensPedido.getPedido())) {
                throw new ConstraintException("Já existe um item de pedido com o ID " + novoItensPedido.getPedido() + ".");
            }
            return itensPedidoRepository.save(novoItensPedido).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Falha ao tentar criar o item do pedido com ID " + novoItensPedido.getPedido() + ".");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição ao salvar o item de pedido com ID " + novoItensPedido.getPedido() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Ação negada. O item do pedido " + novoItensPedido.getPedido() + " violou regras de negócio.");
        } catch (RuntimeException e) {
            // Captura qualquer erro relacionado ao banco de dados ou outros erros de execução
            throw new RuntimeException("Falha ao tentar salvar o item de pedido " + novoItensPedido.getPedido() + ". Erro inesperado.", e);
        }
    }

    @Transactional
    public ItensPedidoDTO atualizar(ItensPedidoModel itensPedidoExistente) {
        try {
            // Verifica se o item de pedido existe antes de tentar atualizar
            if (!itensPedidoRepository.existsByPedido(itensPedidoExistente.getPedido())) {
                throw new ConstraintException("Não foi possível encontrar o item de pedido com o ID " + itensPedidoExistente.getPedido() + ".");
            }
            return itensPedidoRepository.save(itensPedidoExistente).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Falha ao tentar atualizar o item de pedido com ID " + itensPedidoExistente.getPedido() + ".");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar item de pedido com ID " + itensPedidoExistente.getPedido() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro ao tentar atualizar o item do pedido " + itensPedidoExistente.getPedido() + ": Violação de regra de negócio.");
        } catch (RuntimeException e) {
            // Captura qualquer erro relacionado ao banco de dados ou outros erros de execução
            throw new RuntimeException("Falha ao tentar atualizar o item de pedido " + itensPedidoExistente.getPedido() + ". Erro inesperado.", e);
        }
    }

    @Transactional
    public void deletar(ItensPedidoModel itensPedidoExistente) {
        try {
            // Verifica se o item de pedido existe antes de tentar excluir
            if (!itensPedidoRepository.existsByPedido(itensPedidoExistente.getPedido())) {
                throw new ConstraintException("Não foi possível encontrar o item de pedido com ID " + itensPedidoExistente.getPedido() + " para exclusão.");
            }
            itensPedidoRepository.delete(itensPedidoExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro ao tentar deletar o item de pedido com ID " + itensPedidoExistente.getPedido() + ".");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Falha ao excluir item de pedido com ID " + itensPedidoExistente.getPedido() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Violação de regra de negócio ao tentar excluir item de pedido " + itensPedidoExistente.getPedido() + ".");
        } catch (RuntimeException e) {
            // Captura qualquer erro relacionado ao banco de dados ou outros erros de execução
            throw new RuntimeException("Erro na conexão com o banco de dados ao excluir item de pedido " + itensPedidoExistente.getPedido() + ".", e);
        }
    }
}
