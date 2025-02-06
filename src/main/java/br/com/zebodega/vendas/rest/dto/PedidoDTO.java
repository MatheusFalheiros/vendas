package br.com.zebodega.vendas.rest;


import br.com.zebodega.vendas.model.PedidoModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
public class PedidoDTO {

    private float valorTotal;

    private LocalDate dataHora;

    private String numeroPedido;

    private boolean ativo;

    private String observacao;

    private Long idFormaPagamento;

    private Long idCliente;

    public PedidoModel toModel(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PedidoModel.class);
    }
}
