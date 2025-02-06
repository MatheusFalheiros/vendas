package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.repository.ClienteRepository;
import br.com.zebodega.vendas.rest.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public ClienteDTO obterPorId(Long id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente com ID " + id + " não encontrado."));
        return cliente.toDTO();
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> obterTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteModel::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO salvar(ClienteModel novoCliente) {
        if (clienteRepository.existsByCpf(novoCliente.getCpf())) {
            throw new ConstraintException("Já existe um cliente cadastrado com esse CPF: " + novoCliente.getCpf());
        }
        if (clienteRepository.existsByEmail(novoCliente.getEmail())) {
            throw new ConstraintException("Já existe um cliente com esse E-MAIL: " + novoCliente.getEmail());
        }
        return clienteRepository.save(novoCliente).toDTO();
    }

    @Transactional
    public ClienteDTO atualizar(ClienteModel clienteExistente) {
        ClienteModel cliente = clienteRepository.findById(clienteExistente.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado com ID: " + clienteExistente.getId()));
        cliente.setNome(clienteExistente.getNome());
        cliente.setEmail(clienteExistente.getEmail());
        cliente.setCpf(clienteExistente.getCpf());
        return clienteRepository.save(cliente).toDTO();
    }

    @Transactional
    public void deletar(Long id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado com ID: " + id));
        clienteRepository.delete(cliente);
    }
}
