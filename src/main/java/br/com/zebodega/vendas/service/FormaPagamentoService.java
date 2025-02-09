package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.FormaPagamentoModel;
import br.com.zebodega.vendas.repository.FormaPagamentoRepository;
import br.com.zebodega.vendas.rest.dto.FormaPagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional(readOnly = true)
    public FormaPagamentoDTO obterPorId(Long id) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrada."));
        return formaPagamento.toDTO();
    }

    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> obterTodos() {
        List<FormaPagamentoModel> listaFormaPagamentos = formaPagamentoRepository.findAll();
        return listaFormaPagamentos.stream()
                .map(FormaPagamentoModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormaPagamentoDTO salvar(FormaPagamentoModel novaFormaPagamento) {
        verificarExistenciaPorId(novaFormaPagamento.getIdFormaPagamento(), false);
        return salvarOuAtualizarFormaPagamento(novaFormaPagamento);
    }

    @Transactional
    public FormaPagamentoDTO atualizar(FormaPagamentoModel formaPagamentoExistente) {
        verificarExistenciaPorId(formaPagamentoExistente.getIdFormaPagamento(), true);
        return salvarOuAtualizarFormaPagamento(formaPagamentoExistente);
    }

    @Transactional
    public void deletar(FormaPagamentoModel formaPagamentoExistente) {
        verificarExistenciaPorId(formaPagamentoExistente.getIdFormaPagamento(), true);
        try {
            formaPagamentoRepository.delete(formaPagamentoExistente);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro ao deletar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento());
        } catch (SQLException e) {
            throw new SQLException("Erro de conexão ao deletar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento());
        }
    }

    // Método auxiliar para verificar existência de forma de pagamento
    private void verificarExistenciaPorId(Long id, boolean deveExistir) {
        boolean exists = formaPagamentoRepository.existsByIdFormaPagamento(id);
        if (deveExistir && !exists) {
            throw new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrada.");
        }
        if (!deveExistir && exists) {
            throw new ConstraintException("Já existe uma forma de pagamento com ID " + id + ".");
        }
    }

    // Método auxiliar para salvar ou atualizar forma de pagamento
    private FormaPagamentoDTO salvarOuAtualizarFormaPagamento(FormaPagamentoModel formaPagamento) {
        try {
            return formaPagamentoRepository.save(formaPagamento).toDTO();
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro ao salvar/atualizar a forma de pagamento " + formaPagamento.getIdFormaPagamento());
        } catch (ConstraintException e) {
            throw new ConstraintException("Erro de restrição ao salvar/atualizar a forma de pagamento " + formaPagamento.getIdFormaPagamento());
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Violação de regra de negócio ao salvar/atualizar a forma de pagamento " + formaPagamento.getIdFormaPagamento());
        } catch (SQLException e) {
            throw new SQLException("Erro de conexão ao salvar/atualizar a forma de pagamento " + formaPagamento.getIdFormaPagamento());
        }
    }
}
