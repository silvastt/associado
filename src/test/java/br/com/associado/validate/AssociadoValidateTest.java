package br.com.associado.validate;

import br.com.associado.dto.AssociadoDto;
import br.com.associado.error.ErroInternoException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AssociadoValidateTest {

    private AssociadoValidate associadoValidate;

    @Before
    public void setup() {
        associadoValidate = new AssociadoValidate();
    }

    @Test
    public void deveValidarComSucesso() throws Exception {
        associadoValidate.validate(buildAssociado());
    }

    @Test
    public void deveValidarCpfVazio() throws Exception {
        AssociadoDto associado = buildAssociado();
        associado.setCpf(null);

        try {
            associadoValidate.validate(associado);
        } catch (ErroInternoException e) {
            Assert.assertEquals("CPF do Associado não informado!", e.getMessage());
        }
    }

    @Test
    public void deveValidarNomeVazio() throws Exception {
        AssociadoDto associado = buildAssociado();
        associado.setNome(null);

        try {
            associadoValidate.validate(associado);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Nome do Associado não informado!", e.getMessage());
        }
    }

    @Test
    public void deveValidarAssociadoVazio() throws Exception {
        try {
            associadoValidate.validate(null);
        } catch (ErroInternoException e) {
            Assert.assertEquals("Dados de associado não informados!", e.getMessage());
        }
    }

    private AssociadoDto buildAssociado() {
        return AssociadoDto.builder().cpf("82909598004").nome("Tiago Silva").build();
    }

}