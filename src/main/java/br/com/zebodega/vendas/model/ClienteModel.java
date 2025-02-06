package br.com.zebodega.vendas.model;

import br.com.zebodega.vendas.rest.ClienteDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

/**
 * Representa a entidade Cliente, responsável por armazenar os dados de um cliente
 * no sistema e mapeá-los para a base de dados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cliente")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Alterado de idCliente para id

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 255, message = "O nome não pode ultrapassar 255 caracteres")
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 caracteres")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido!")
    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 14, message = "O telefone não pode ultrapassar 14 caracteres")
    @Column(name = "telefone", length = 14, nullable = false)
    private String telefone;

    @NotNull(message = "A data de nascimento não pode ser nula")
    @Past(message = "A data de nascimento deve ser anterior à data atual")
    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O sexo é obrigatório")
    @Pattern(regexp = "^[MF]$", message = "O sexo deve ser 'M' para masculino ou 'F' para feminino.")
    @Size(min = 1, max = 1, message = "O sexo deve ter apenas 1 caractere")
    @Column(name = "sexo", length = 1, nullable = false)
    private String sexo;

    @Size(max = 255, message = "O apelido não pode ultrapassar 255 caracteres")
    @Column(name = "apelido", length = 255)
    private String apelido;

    /**
     * Converte a entidade ClienteModel para seu correspondente DTO (ClienteDTO).
     *
     * @return Uma instância de ClienteDTO com os dados mapeados a partir do ClienteModel.
     */
    public ClienteDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ClienteDTO.class);
    }
}
