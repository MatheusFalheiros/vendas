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

@Data //get, set...
@AllArgsConstructor //Serve para colocar todos os argumentos no Constructor
@NoArgsConstructor //Serve para criar um Constructor sem argumentos
@Entity //Serve para informar que uma classe também é entidade
@Table(name = "Cliente")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Size(message = "O valor máximo não pode ultrapassar 255 carecteres",max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Size(message = "O valor mínimo e máximo deve ser de 11 carecteres", min = 11, max = 11)
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @CPF(message = "CPF inválido!")
    private String cpf;


    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Column(name = "email", length = 255, nullable = false, unique = true)
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Size(message = "O valor máximo não pode ultrapassar 14 carecteres", max = 14)
    @Column(name = "telefone", length = 14, nullable = false)
    private String telefone;

    @NotNull(message = "O valor não pode ser nulo")
    @Column(name = "dataNascimento", nullable = false)
    @Past(message = "Data de nascimento deve ser menor do que a atual!")
    private LocalDate dataNascimento;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Pattern(regexp = "^[MF]$", message = "O sexo deve ser 'M' caso masculino ou 'F' caso feminino.")
    @Size(message = "O valor máximo não pode ultrapassar 1 carecter",min = 1, max = 1)
    @Column(name = "sexo", length = 1, nullable = false)
    private String sexo;

    @NotBlank(message = "O valor é obrigatório")
    @NotNull(message = "O valor não pode ser nulo")
    @Size(message = "O valor máximo não pode ultrapassar 255 carecteres",max = 255)
    @Column(name = "apelido", length = 255, nullable = true)
    private  String apelido;

    /**
     * Converte a entidade ClienteModel para seu correspondente DTO (ClienteDTO).
     *
     * @return Uma instância de ClienteDTO com os dados mapeados a partir do ClienteModel.
     */

    public ClienteDTO toDTO(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ClienteDTO.class);
    }
}


