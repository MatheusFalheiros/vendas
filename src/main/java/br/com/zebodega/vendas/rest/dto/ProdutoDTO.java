package br.com.zebodega.vendas.rest.dto;

import br.com.zebodega.vendas.model.ProdutoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ProdutoDTO {


    private String nome;

    private String descricao;

    private  float preco;

    private boolean ativo;

    public ProdutoModel toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoModel.class);
    }
}
