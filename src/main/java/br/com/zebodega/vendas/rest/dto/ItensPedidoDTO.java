package br.com.zebodega.vendas.rest;


import br.com.zebodega.vendas.model.ItensPedidoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ItensPedidoDTO {

    private int quantidade;

    private Long idPedido;

    private Long idProduto;

    public ItensPedidoModel toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ItensPedidoModel.class);
    }
}
