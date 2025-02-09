package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.dto.ItensPedidoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

/**
 * Representa a entidade ItensPedido, responsável por armazenar os dados dos itens pedidos
 * no sistema e mapeá-los para a base de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ItensPedidos") // Nome pluralizado para melhor prática
public class ItensPedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItensPedido;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @NotNull(message = "Vinculação a um pedido é obrigatória!")
    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private PedidoModel pedido;

    @NotNull(message = "Adicionar um produto é obrigatório!")
    @ManyToOne
    @JoinColumn(name = "idProduto", nullable = false)
    private ProdutoModel produto;

    /**
     * Converte a entidade ItensPedidoModel para seu correspondente DTO.
     *
     * @return Uma instância de ItensPedidoDTO com os dados mapeados a partir do ItensPedidoModel.
     */
    public ItensPedidoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ItensPedidoDTO.class);
    }
}
