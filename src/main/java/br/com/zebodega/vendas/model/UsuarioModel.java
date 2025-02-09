package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.dto.UsuarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

/**
 * Representa a entidade Usuario, responsável por armazenar os dados de um usuário
 * no sistema e mapeá-los para a base de dados.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "O valor é obrigatório")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres", max = 255)
    @Column(name = "userName", length = 255, nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "O valor é obrigatório")
    @Size(message = "O valor máximo não pode ultrapassar 255 caracteres", max = 255)
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @NotNull(message = "O valor não pode ser nulo!")
    @Min(value = 0, message = "O valor mínimo para o campo ativo é 0")
    @Max(value = 1, message = "O valor máximo para o campo ativo é 1")
    @Column(name = "ativo", length = 1, nullable = false)
    private byte ativo;

    @NotNull(message = "Vinculação a um cliente é obrigatório!")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false, unique = true)
    private ClienteModel cliente;

    /**
     * Converte a entidade UsuarioModel para seu correspondente DTO.
     *
     * @return Uma instância de UsuarioDTO com os dados mapeados a partir do UsuarioModel.
     */
    public UsuarioDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsuarioDTO.class);
    }
}
