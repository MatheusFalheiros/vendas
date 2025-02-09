package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.ClienteModel;
import br.com.zebodega.vendas.model.UsuarioModel;
import br.com.zebodega.vendas.repository.UsuarioRepository;
import br.com.zebodega.vendas.rest.ClienteDTO;
import br.com.zebodega.vendas.rest.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository  usuarioRepository;

    @Transactional(readOnly = true)
    public UsuarioDTO obterPorId(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario com ID " + id + " não encontrado."));
        return usuario.toDTO();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obterTodos(){

        List<UsuarioModel> listaUsuarios = usuarioRepository.findAll();

        return listaUsuarios.stream().map(usuarioModel -> usuarioModel.toDTO())
                .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO salvar(UsuarioModel novoUsuario){


        try {
            // Caso ocorra a tentaiva de salvar um usuário com o id cliente já existente, mostre a exceção abaixo.
            if (usuarioRepository.existsByCliente(novoUsuario.getCliente())) {
                throw new ConstraintException("Já existe um usuário cadastrado com esse id" + novoUsuario.getCliente() + " !");
            }

            return usuarioRepository.save(novoUsuario).toDTO();
        }catch (DataIntegrityException e ){
            throw new DataIntegrityException("Erro! Não foi possível criar um novo usuário! " + novoUsuario.getCliente());
        }catch (ConstraintException e){
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o usuario " + novoUsuario.getCliente() + ".");
            }
            throw e;
        }catch (BusinessRuleException e){
            throw  new BusinessRuleException("Erro! Não foi possível criar um novo usuário " + novoUsuario.getCliente() + ",pois violou uma regra de negócio");
        }catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível criar um novo usuário" + novoUsuario.getCliente() + ", pois houve um erro de conexão com o banco de dados");
        }
    }

    @Transactional
    public UsuarioDTO atualizar(UsuarioModel usuarioExistente){


        try {
            // Caso ocorra a tentaiva de salvar um usuário com o id cliente já existente, mostre a exceção abaixo.
            if (!usuarioRepository.existsByCliente(usuarioExistente.getCliente())) {
                throw new ConstraintException("O usuário " + usuarioExistente.getCliente() + " não foi encontrado!");
            }

            return usuarioRepository.save(usuarioExistente).toDTO();
        }catch (DataIntegrityException e ){
            throw new DataIntegrityException("Erro! Não foi possível atualizar o usuário! " + usuarioExistente.getCliente());
        }catch (ConstraintException e){
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o usuario " + usuarioExistente.getCliente() + ": Restrição de integridade de dados.");
            }
            throw e;
        }catch (BusinessRuleException e){
            throw  new BusinessRuleException("Erro! Não foi possível atualizar o usuário " + usuarioExistente.getCliente() + ",pois violou uma regra de negócio");
        }catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o usuário" + usuarioExistente.getCliente() + ", pois houve um erro de conexão com o banco de dados");
        }catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Erro! não foi possível localizar o usuário " + usuarioExistente.getCliente() + " no banco de dados");
        }
    }

    @Transactional
    public void deletar(UsuarioModel usuarioExistente){


        try {
            // Caso ocorra a tentaiva de salvar um usuário com o id cliente já existente, mostre a exceção abaixo.
            if (!usuarioRepository.existsByCliente(usuarioExistente.getCliente())) {
                throw new ConstraintException("O usuário " + usuarioExistente.getCliente() + " não foi encontrado!");
            }

             usuarioRepository.delete(usuarioExistente);
        }catch (DataIntegrityException e ){
            throw new DataIntegrityException("Erro! Não foi possível deletar o usuário! " + usuarioExistente.getCliente());
        }catch (ConstraintException e){
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o usuario " + usuarioExistente.getCliente() + ": Restrição de integridade de dados.");
            }
            throw e;
        }catch (BusinessRuleException e){
            throw  new BusinessRuleException("Erro! Não foi possível deletar o usuário " + usuarioExistente.getCliente() + ",pois violou uma regra de negócio");
        }catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar o usuário" + usuarioExistente.getCliente() + ", pois houve um erro de conexão com o banco de dados");
        }catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Erro! não foi possível localizar o usuário " + usuarioExistente.getCliente() + " no banco de dados");
        }
    }

}
