package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.FormaPagamentoModel;
import br.com.zebodega.vendas.repository.FormaPagamentoRepository;
import br.com.zebodega.vendas.rest.FormaPagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelas operações relacionadas as Formas de pagamento.
 */
@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional(readOnly = true)
    public FormaPagamentoDTO obterPorId(Long id) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrado."));
        return formaPagamento.toDTO();
    }

    @Transactional(readOnly = true)
    public List<FormaPagamentoDTO> obterTodos(){
        List<FormaPagamentoModel> listaFormaPagamentos = formaPagamentoRepository.findAll();

        return listaFormaPagamentos.stream().map(formaPagamentoModel -> formaPagamentoModel.toDTO())
                .collect(Collectors.toList());
    }

    @Transactional
    public FormaPagamentoDTO salvar(FormaPagamentoModel novaFormaPagamento){

        try {
            // Caso ocorra a tentaiva de salvar uma forma de pagamento com o id já existente, mostre a exceção abaixo.
            if (formaPagamentoRepository.existsByIdFormaPagamento(novaFormaPagamento.getIdFormaPagamento())) {
                throw new ConstraintException("Já existe uma forma de pagamento cadastrado com esse id" + novaFormaPagamento.getIdFormaPagamento() + " !");
            }

            return formaPagamentoRepository.save(novaFormaPagamento).toDTO();
        }catch (DataIntegrityException e ){
            throw new DataIntegrityException("Erro! Não foi possível criar uma nova forma de pagamento! " + novaFormaPagamento.getIdFormaPagamento());
        }catch (ConstraintException e){
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a forma de pagamento " + novaFormaPagamento.getIdFormaPagamento() + ".");
            }
            throw e;
        }catch (BusinessRuleException e){
            throw  new BusinessRuleException("Erro! Não foi possível criar uma nova forma de pagamento " + novaFormaPagamento.getIdFormaPagamento() + ",pois violou uma regra de negócio");
        }catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível criar uma nova forma de pagamento" + novaFormaPagamento.getIdFormaPagamento() + ", pois houve um erro de conexão com o banco de dados");
        }
    }

    @Transactional
    public FormaPagamentoDTO atualizar(FormaPagamentoModel formaPagamentoExistente){


        try {
            // Caso ocorra a tentaiva de salvar uma forma de pagamento com o id já existente, mostre a exceção abaixo.
            if (!formaPagamentoRepository.existsByIdFormaPagamento(formaPagamentoExistente.getIdFormaPagamento())) {
                throw new ConstraintException("A forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + " não foi encontrada!");
            }

            return formaPagamentoRepository.save(formaPagamentoExistente).toDTO();
        }catch (DataIntegrityException e ){
            throw new DataIntegrityException("Erro! Não foi possível atualizar a forma de pagamento! " + formaPagamentoExistente.getIdFormaPagamento());
        }catch (ConstraintException e){
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a Forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + ": Restrição de integridade de dados.");
            }
            throw e;
        }catch (BusinessRuleException e){
            throw  new BusinessRuleException("Erro! Não foi possível atualizar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + ",pois violou uma regra de negócio");
        }catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a forma de pagamento" + formaPagamentoExistente.getIdFormaPagamento() + ", pois houve um erro de conexão com o banco de dados");
        }catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Erro! não foi possível localizar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + " no banco de dados");
        }
    }

    @Transactional
    public void deletar(FormaPagamentoModel formaPagamentoExistente){


        try {
            // Caso ocorra a tentaiva de salvar uma forma de pagamento com o id já existente, mostre a exceção abaixo.
            if (!formaPagamentoRepository.existsByIdFormaPagamento(formaPagamentoExistente.getIdFormaPagamento())) {
                throw new ConstraintException("A forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + " não foi encontrada!");
            }

            formaPagamentoRepository.delete(formaPagamentoExistente);
        }catch (DataIntegrityException e ){
            throw new DataIntegrityException("Erro! Não foi possível deletar a forma de pagamento! " + formaPagamentoExistente.getIdFormaPagamento());
        }catch (ConstraintException e){
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + ": Restrição de integridade de dados.");
            }
            throw e;
        }catch (BusinessRuleException e){
            throw  new BusinessRuleException("Erro! Não foi possível deletar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + ",pois violou uma regra de negócio");
        }catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar a forma de pagamento" + formaPagamentoExistente.getIdFormaPagamento() + ", pois houve um erro de conexão com o banco de dados");
        }catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Erro! não foi possível localizar a forma de pagamento " + formaPagamentoExistente.getIdFormaPagamento() + " no banco de dados");
        }
    }

}
