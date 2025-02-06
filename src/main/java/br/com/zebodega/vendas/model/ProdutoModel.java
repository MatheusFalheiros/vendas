package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.ProdutoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

/**
 * Representa a entidade Produto, responsável por armazenar os dados de um produto
 * no sistema e mapeá-los para a base de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Produto")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres", max = 255)
    @Column(name = "nome", length = 255, nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres", max = 255)
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @NotNull(message = "O valor não pode ser nulo")
    @DecimalMin(value = "0.0", message = "O preço não pode ser negativo")
    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 0, message = "O valor mínimo para o campo ativo é 0")
    @Max(value = 1, message = "O valor máximo para o campo ativo é 1")
    @Column(name = "ativo", length = 1, nullable = false)
    private byte ativo;

    /**
     * Converte a entidade ProdutoModel para seu correspondente DTO.
     *
     * @return Uma instância de ProdutoDTO com os dados mapeados a partir do ProdutoModel.
     */
    public ProdutoDTO toDTO(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProdutoDTO.class);
    }
}
