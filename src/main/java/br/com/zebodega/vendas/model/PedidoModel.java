package br.com.zebodega.vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import br.com.zebodega.vendas.rest.PedidoDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa a entidade Pedido, responsável por armazenar os dados de um pedido
 * no sistema e mapeá-los para a base de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedido")
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;  // O campo 'idPedido' é gerado automaticamente pelo banco

    @NotBlank(message = "O número do pedido é obrigatório")
    @Column(name = "numeroPedido", length = 255, nullable = false, unique = true)
    private String numeroPedido;

    @NotNull(message = "O valor total não pode ser nulo")
    @DecimalMin(value = "0.0", message = "O valor total deve ser maior ou igual a zero")
    @Column(name = "valorTotal", nullable = false)
    private BigDecimal valorTotal;  // Mudado para BigDecimal

    @NotNull(message = "A data de criação não pode ser nula")
    @Column(name = "dataCriacao", nullable = false)
    private LocalDate dataCriacao;

    @NotBlank(message = "O status do pedido é obrigatório")
    @Column(name = "status", length = 50, nullable = false)
    private String status;  // Adicionada a propriedade 'status'

    /**
     * Converte a entidade PedidoModel para seu correspondente DTO (PedidoDTO).
     *
     * @return Uma instância de PedidoDTO com os dados mapeados a partir do PedidoModel.
     */
    public PedidoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PedidoDTO.class);
    }

}
