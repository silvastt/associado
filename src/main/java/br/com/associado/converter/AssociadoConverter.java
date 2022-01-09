package br.com.associado.converter;

import br.com.associado.bo.Associado;
import br.com.associado.dto.AssociadoDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.stereotype.Component;

import static br.com.associado.util.MessagesProperties.ERRO_CONVERSAO_ASSOCIADO;

@Component("AssociadoConverter")
public class AssociadoConverter {

    private ModelMapper mapper;

    public AssociadoConverter(final ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Associado toModel(final AssociadoDto dto) {
        Assert.notNull(dto, ERRO_CONVERSAO_ASSOCIADO);
        return mapper.map(dto, Associado.class);
    }

    public AssociadoDto toDTO(final Associado associado) {
        Assert.notNull(associado, ERRO_CONVERSAO_ASSOCIADO);
        return AssociadoDto.builder()
                           .id(associado.getId().toString())
                           .cpf(associado.getCpf())
                           .nome(associado.getNome())
                           .build();
    }

}
