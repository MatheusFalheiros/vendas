package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.PedidoModel;
import br.com.zebodega.vendas.rest.PedidoDTO;
import br.com.zebodega.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos pedidos.
 */
@RestController
@RequestMapping("/api/pedidos")  // Ajustando o caminho para /api/pedidos
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> obterTodos() {
        return pedidoService.obterTodos();
    }

    @GetMapping("/{id}")
    public PedidoDTO obterPorId(@PathVariable Long id) {
        return pedidoService.obterPorId(id);
    }

    @PostMapping
    public PedidoDTO salvar(@RequestBody PedidoModel novoPedido) {
        return pedidoService.salvar(novoPedido);
    }

    @PutMapping("/{id}")
    public PedidoDTO atualizar(@PathVariable Long id, @RequestBody PedidoModel pedidoAtualizado) {
        // Aqui definimos o ID corretamente
        pedidoAtualizado.setIdPedido(id);  // Usando o método setIdPedido(id)
        return pedidoService.atualizar(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        PedidoModel pedido = new PedidoModel();
        pedido.setIdPedido(id);  // Usando o método setIdPedido(id) para definir o ID
        pedidoService.deletar(pedido);
    }
}
