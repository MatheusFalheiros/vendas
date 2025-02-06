package br.com.zebodega.vendas.rest;

import br.com.zebodega.vendas.model.UsuarioModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UsuarioDTO {


    private String userName;

    private  String password;

    private boolean ativo;

    private Long idCliente;

    public UsuarioModel toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsuarioModel.class);
    }
}
