package br.com.zebodega.vendas.rest.controller;

import br.com.zebodega.vendas.model.UsuarioModel;
import br.com.zebodega.vendas.rest.dto.UsuarioDTO;
import br.com.zebodega.vendas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos usuários.
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obterTodos() {

        List<UsuarioDTO> usuarios = usuarioService.obterTodos();
        return ResponseEntity.ok(usuarios);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterPorId(@PathVariable Long id) {

        UsuarioDTO usuario = usuarioService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);

    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@Valid @RequestBody UsuarioModel novoUsuario) {

        UsuarioDTO novoUsuarioDTO = usuarioService.salvar(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuarioDTO);

    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizar(@Valid @RequestBody UsuarioModel usuarioExistente) {

        UsuarioDTO usuarioAtualizadoDTO = usuarioService.atualizar(usuarioExistente);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizadoDTO);

    }

    @DeleteMapping
    public void deletar(@Valid @RequestBody UsuarioModel usuarioExistente) {

        usuarioService.deletar(usuarioExistente);

    }

}