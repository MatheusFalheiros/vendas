package br.com.zebodega.vendas.rest.dto;

import br.com.zebodega.vendas.model.ClienteModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para encapsular dados do cliente em operações de entrada e saída na API.
 */

@Data
public class ClienteDTO {


        private String nome;

        private String cpf;

        private String email;

        private String telefone;

        private LocalDate dataNascimento;

        private String sexo;

        private  String apelido;

        /**
         * Converte o DTO (ClienteDTO) para sua entidade correspondente (ClienteModel).
         *
         * @return Uma instância de ClienteModel com os dados mapeados a partir do ClienteDTO.
         */

        public ClienteModel toModel(){
                ModelMapper modelMapper = new ModelMapper();
                return modelMapper.map(this, ClienteModel.class);
        }
    }




