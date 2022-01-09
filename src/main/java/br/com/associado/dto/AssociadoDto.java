package br.com.associado.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssociadoDto {
    private String id;
    private String nome;
    private String cpf;
}
