package br.com.associado.validate;

import br.com.associado.dto.AssociadoDto;
import br.com.associado.error.ErroInternoException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static br.com.associado.util.MessagesProperties.*;

@Component("AssociadoValidate")
public class AssociadoValidate {

    public AssociadoValidate() {}

    public void validate(final AssociadoDto dto) {
        if (Objects.isNull(dto)) {
            throw new ErroInternoException(ASSOCIADO_VAZIO);
        }

        if (StringUtils.isEmpty(dto.getNome())) {
            throw new ErroInternoException(NOME_ASSOCIADO_VAZIO);
        }

        if (StringUtils.isEmpty(dto.getCpf())) {
            throw new ErroInternoException(CPF_ASSOCIADO_VAZIO);
        }
    }
}
