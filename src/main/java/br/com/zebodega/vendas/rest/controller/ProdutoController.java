package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.ProdutoModel;
import br.com.zebodega.vendas.rest.dto.ProdutoDTO;
import br.com.zebodega.vendas.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos produtos.
 */
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> obterTodos() {

        List<ProdutoDTO> produtos = produtoService.obterTodos();
        return ResponseEntity.ok(produtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> obterPorId(@PathVariable Long id) {

        ProdutoDTO produto = produtoService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(produto);

    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@Valid @RequestBody ProdutoModel novoProduto) {

        ProdutoDTO novoProdutoDTO = produtoService.salvar(novoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProdutoDTO);

    }

    @PutMapping
    public ResponseEntity<ProdutoDTO> atualizar(@Valid @RequestBody ProdutoModel produtoExistente) {

        ProdutoDTO produtoAtualizadoDTO = produtoService.atualizar(produtoExistente);
        return ResponseEntity.status(HttpStatus.OK).body(produtoAtualizadoDTO);

    }

    @DeleteMapping
    public void deletar(@Valid @RequestBody ProdutoModel produtoExistente) {

        produtoService.deletar(produtoExistente);

    }

}