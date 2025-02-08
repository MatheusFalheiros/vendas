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
    private Long idCliente;

    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(message = "O nome máximo não pode ultrapassar 255 caracteres!", max = 255)
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull(message = "O CPF não pode ser nulo.")
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(message = "O CPF deve conter exatamente 11 caracteres.", min = 11, max = 11)
    @CPF(message = "CPF inválido. Verifique o valor informado.")
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "O e-mail não pode ser nulo.")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido. Verifique o valor informado.")
    @Column(name = "email", length = 255, nullable = false, unique = true)
    @Email(message = "E-mail inválido. Verifique o valor informado.")
    private String email;

    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(message = "O telefone deve conter exatamente 11 caracteres.", min = 11, max = 11)
    @Column(name = "telefone", length = 11, nullable = false, unique = true)
    private String telefone;

    @NotNull(message = "A data de nascimento não pode ser nula.")
    @Past(message = "A data de nascimento informada deve ser anterior ao dia atual.")
    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "O sexo não pode ser nulo.")
    @NotBlank(message = "O sexo é obrigatório.")
    @Pattern(regexp = "^[MF]$", message = "O sexo deve ser 'M' para masculino ou 'F' para feminino.")
    @Size(min = 1, max = 1, message = "O sexo deve conter apenas 1 caracter (M ou F).")
    @Column(name = "sexo", length = 1, nullable = false)
    private String sexo;

    @NotBlank(message = "O apelido é obrigatório!")
    @NotNull(message = "O apelido não pode ser nulo!")
    @Size(message = "O apelido máximo não pode ultrapassar 255 caracteres!", max = 255)
    @Column(name = "apelido", length = 255, nullable = true)
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
