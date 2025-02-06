package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.PedidoModel;
import br.com.zebodega.vendas.repository.PedidoRepository;
import br.com.zebodega.vendas.rest.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public PedidoDTO obterPorId(Long id) {
        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido com ID " + id + " não encontrado."));
        return pedido.toDTO();
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> obterTodos() {
        List<PedidoModel> listaPedidos = pedidoRepository.findAll();
        return listaPedidos.stream().map(PedidoModel::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO salvar(PedidoModel novoPedido) {
        if (pedidoRepository.existsByNumeroPedido(novoPedido.getNumeroPedido())) {
            throw new ConstraintException("Já existe um pedido cadastrado com esse número: " + novoPedido.getNumeroPedido() + " !");
        }
        return pedidoRepository.save(novoPedido).toDTO();
    }

    @Transactional
    public PedidoDTO atualizar(PedidoModel pedidoExistente) {
        if (!pedidoRepository.existsByNumeroPedido(pedidoExistente.getNumeroPedido())) {
            throw new ConstraintException("O Pedido " + pedidoExistente.getNumeroPedido() + " não foi encontrado!");
        }
        return pedidoRepository.save(pedidoExistente).toDTO();
    }

    @Transactional
    public void deletar(PedidoModel pedidoExistente) {
        if (!pedidoRepository.existsByNumeroPedido(pedidoExistente.getNumeroPedido())) {
            throw new ConstraintException("O Pedido " + pedidoExistente.getNumeroPedido() + " não foi encontrado!");
        }
        pedidoRepository.delete(pedidoExistente);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularFaturamentoPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }
        List<PedidoModel> pedidos = pedidoRepository.findByDataCriacaoBetweenAndStatus(dataInicial, dataFinal, "ATIVO");

        return pedidos.stream()
                .map(PedidoModel::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public BigDecimal aplicarDescontoPedido(PedidoModel pedido) {
        if (!"ATIVO".equalsIgnoreCase(pedido.getStatus())) {
            throw new BusinessRuleException("Apenas pedidos ativos podem receber descontos.");
        }

        BigDecimal valorTotal = pedido.getValorTotal();
        BigDecimal desconto = BigDecimal.ZERO;

        if (valorTotal.compareTo(new BigDecimal("500")) > 0 && valorTotal.compareTo(new BigDecimal("1000")) <= 0) {
            desconto = valorTotal.multiply(new BigDecimal("0.05"));
        } else if (valorTotal.compareTo(new BigDecimal("1000")) > 0) {
            desconto = valorTotal.multiply(new BigDecimal("0.10"));
        }

        pedido.setValorTotal(valorTotal.subtract(desconto));
        pedidoRepository.save(pedido);
        return pedido.getValorTotal();
    }
}
