package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.rest.ClienteDTO;
import br.com.zebodega.vendas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos clientes.
 */
@RestController
@RequestMapping("/cliente")  // Corrigido o caminho para "/api/clientes"
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Obtém a lista de todos os clientes cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obterTodos() {
        List<ClienteDTO> clienteDTOList = clienteService.obterTodos();
        return ResponseEntity.ok(clienteDTOList);
    }

    /**
     * Obtém um cliente pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obterPorId(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.obterPorId(id);
        return ResponseEntity.ok(clienteDTO);
    }

    /**
     * Salva um novo cliente na base de dados.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteModel novoCliente) {
        ClienteDTO novoClienteDTO = clienteService.salvar(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteDTO);
    }

    /**
     * Atualiza os dados de um cliente existente.
     */
    @PutMapping("/{id}") // Agora a atualização exige o ID na URL
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteModel clienteAtualizado) {
        clienteAtualizado.setId(id); // Alterado para setId(id)
        ClienteDTO clienteAtualizadoDTO = clienteService.atualizar(clienteAtualizado);
        return ResponseEntity.ok(clienteAtualizadoDTO);
    }

    /**
     * Deleta um cliente da base de dados pelo ID.
     */
    @DeleteMapping("/{id}") // Agora a exclusão ocorre pelo ID na URL
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);  // Agora o método recebe apenas o ID
        return ResponseEntity.noContent().build();
    }
}
