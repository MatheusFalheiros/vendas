package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.FormaPagamentoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

/**
 * Representa a entidade FormaPagamento, responsável por armazenar os dados de uma forma de pagamento
 * no sistema e mapeá-los para a base de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FormasPagamento") // Melhor prática para tabelas no plural
public class FormaPagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormaPagamento;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 255, message = "O nome não pode ultrapassar 255 caracteres")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição não pode ultrapassar 255 caracteres")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    /**
     * Converte a entidade FormaPagamentoModel para seu correspondente DTO.
     *
     * @return Uma instância de FormaPagamentoDTO com os dados mapeados a partir do FormaPagamentoModel.
     */
    public FormaPagamentoDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FormaPagamentoDTO.class);
    }
}
