package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.rest.dto.ClienteDTO;
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
@RequestMapping("/cliente")
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
     *
     * @param id ID do cliente.
     * @return ClienteDTO representando o cliente encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obterPorId(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
    }

    /**
     * Salva um novo cliente na base de dados.
     *
     * @param novoCliente ClienteModel contendo os dados do novo cliente.
     * @return ClienteDTO representando o cliente salvo.
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteModel novoCliente) {
        ClienteDTO novoClienteDTO = clienteService.salvar(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteDTO);
    }

    /**
     * Atualiza os dados de um cliente existente.
     *
     * @param clienteExistente ClienteModel contendo os dados atualizados do cliente.
     * @return ClienteDTO representando o cliente atualizado.
     */
    @PutMapping
    public ResponseEntity<ClienteDTO> atualizar(@Valid @RequestBody ClienteModel clienteExistente) {
        ClienteDTO clienteExistenteDTO = clienteService.atualizar(clienteExistente);
        return ResponseEntity.status(HttpStatus.OK).body(clienteExistenteDTO);
    }

    /**
     * Deleta um cliente da base de dados.
     *
     * @param clienteModel ClienteModel contendo os dados do cliente a ser deletado.
     */
    @DeleteMapping
    public void deletar(@Valid @RequestBody ClienteModel clienteModel) {
        clienteService.deletar(clienteModel);
    }
}