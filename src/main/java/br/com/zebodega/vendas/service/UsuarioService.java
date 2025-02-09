package br.com.zebodega.vendas.service;

import br.com.zebodega.vendas.exception.*;
import br.com.zebodega.vendas.model.UsuarioModel;
import br.com.zebodega.vendas.repository.UsuarioRepository;
import br.com.zebodega.vendas.rest.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public UsuarioDTO obterPorId(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário com ID " + id + " não encontrado."));
        return usuario.toDTO();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obterTodos() {
        List<UsuarioModel> listaUsuarios = usuarioRepository.findAll();
        return listaUsuarios.stream().map(UsuarioModel::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO salvar(UsuarioModel novoUsuario) {
        validarUsuario(novoUsuario);
        return usuarioRepository.save(novoUsuario).toDTO();
    }

    @Transactional
    public UsuarioDTO atualizar(UsuarioModel usuarioExistente) {
        validarUsuarioExistente(usuarioExistente);
        return usuarioRepository.save(usuarioExistente).toDTO();
    }

    @Transactional
    public void deletar(UsuarioModel usuarioExistente) {
        validarUsuarioExistente(usuarioExistente);
        usuarioRepository.delete(usuarioExistente);
    }

    private void validarUsuario(UsuarioModel usuario) {
        try {
            if (usuarioRepository.existsByCliente(usuario.getCliente())) {
                throw new ConstraintException("Já existe um usuário cadastrado com o id cliente " + usuario.getCliente() + " !");
            }
        } catch (ConstraintException e) {
            handleException(e, "Erro ao salvar o usuário");
        } catch (Exception e) {
            handleException(e, "Erro ao salvar o usuário");
        }
    }

    private void validarUsuarioExistente(UsuarioModel usuario) {
        try {
            if (!usuarioRepository.existsByCliente(usuario.getCliente())) {
                throw new ObjectNotFoundException("Usuário não encontrado com o id cliente " + usuario.getCliente());
            }
        } catch (Exception e) {
            handleException(e, "Erro ao atualizar/deletar o usuário");
        }
    }

    private void handleException(Exception e, String messagePrefix) {
        if (e instanceof ConstraintException) {
            throw new ConstraintException(messagePrefix + ": " + e.getMessage());
        } else if (e instanceof DataIntegrityException) {
            throw new DataIntegrityException(messagePrefix + " devido a uma violação de integridade de dados.");
        } else if (e instanceof BusinessRuleException) {
            throw new BusinessRuleException(messagePrefix + " devido a uma violação de regra de negócios.");
        } else if (e instanceof SQLException) {
            throw new SQLException(messagePrefix + " devido a um erro de conexão com o banco de dados.");
        } else {
            throw new RuntimeException(messagePrefix + ": " + e.getMessage());
        }
    }
}
