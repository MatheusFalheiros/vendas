package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.FormaPagamentoModel;
import br.com.zebodega.vendas.rest.dto.FormaPagamentoDTO;
import br.com.zebodega.vendas.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas às formas de pagamento.
 */
@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    /**
     * Obtém a lista de todas as formas de pagamento cadastradas.
     *
     * @return Lista de FormaPagamentoDTO representando as formas de pagamento cadastradas.
     */
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> obterTodos() {

        List<FormaPagamentoDTO> formaPagamentoDTOList = formaPagamentoService.obterTodos();
        return ResponseEntity.ok(formaPagamentoDTOList);

    }

    /**
     * Obtém uma forma de pagamento pelo ID.
     *
     * @param id ID da forma de pagamento.
     * @return FormaPagamentoDTO representando a forma de pagamento encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> obterPorId(@PathVariable Long id) {

        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoDTO);

    }

    /**
     * Salva uma nova forma de pagamento na base de dados.
     *
     * @param novaFormaPagamento FormaPagamentoModel contendo os dados da nova forma de pagamento.
     * @return FormaPagamentoDTO representando a forma de pagamento salva.
     */
    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> salvar(@Valid @RequestBody FormaPagamentoModel novaFormaPagamento) {

        FormaPagamentoDTO novaFormaPagamentoDTO = formaPagamentoService.salvar(novaFormaPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFormaPagamentoDTO);

    }

    /**
     * Atualiza os dados de uma forma de pagamento existente.
     *
     * @param formaPagamentoExistente FormaPagamentoModel contendo os dados atualizados.
     * @return FormaPagamentoDTO representando a forma de pagamento atualizada.
     */
    @PutMapping
    public ResponseEntity<FormaPagamentoDTO> atualizar(@Valid @RequestBody FormaPagamentoModel formaPagamentoExistente) {

        FormaPagamentoDTO formaPagamentoExistenteDTO = formaPagamentoService.atualizar(formaPagamentoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(formaPagamentoExistenteDTO);

    }

    /**
     * Deleta uma forma de pagamento da base de dados.
     *
     * @param formaPagamentoModel FormaPagamentoModel contendo os dados da forma de pagamento a ser deletada.
     */
    @DeleteMapping
    public void deletar(@Valid @RequestBody FormaPagamentoModel formaPagamentoModel) {

        formaPagamentoService.deletar(formaPagamentoModel);

    }

}