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

    /**
     * Instância do serviço de clientes, responsável por encapsular a lógica de negócios
     * e intermediar as operações entre o controlador e o repositório.
     */

    @Autowired
    private ClienteService clienteService;

    /**
     * Obtém a lista de todos os clientes cadastrados.
     *
     * @return Lista de ClienteDTO representando os clientes cadastrados.
     */

    @GetMapping()
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
        return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
    }

    /**
     * Salva um novo cliente na base de dados.
     * @return ClienteDTO representando o cliente salvo.
     */

    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteModel novoCliente) {
        ClienteDTO novoClienteDTO = clienteService.salvar(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteDTO);
    }

    /**
     * Atualiza os dados de um cliente existente.
     * @return ClienteDTO representando o cliente atualizado.
     */

    @PutMapping("/{id}") // Agora a atualização exige o ID na URL
    public ResponseEntity<ClienteDTO> atualizar(@Valid @RequestBody ClienteModel clienteExistente) {
        ClienteDTO clienteExistenteDTO = clienteService.atualizar(clienteExistente);
        return ResponseEntity.status(HttpStatus.OK).body(clienteExistenteDTO);
    }

    /**
     * Deleta um cliente da base de dados pelo ID.
     */
    @DeleteMapping("/{id}") // Agora a exclusão ocorre pelo ID na URL
    public void deletar(@Valid @RequestBody ClienteModel clienteModel) {
        clienteService.deletar(clienteModel);
    }
}
