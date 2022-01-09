package br.com.associado.service;

import br.com.associado.bo.Associado;
import br.com.associado.converter.AssociadoConverter;
import br.com.associado.dto.AssociadoDto;
import br.com.associado.error.ErroInternoException;
import br.com.associado.error.ObjetoNaoEncontradoException;
import br.com.associado.repository.AssociadoRepository;
import br.com.associado.validate.AssociadoValidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static br.com.associado.util.MessagesProperties.*;

@Service("AssociadoService")
public class AssociadoService {

    Logger logger = LoggerFactory.getLogger(AssociadoService.class);

    private final AssociadoRepository repository;
    private final AssociadoConverter converter;
    private final AssociadoValidate validate;

    public AssociadoService(
        final AssociadoRepository repository,
        final AssociadoConverter converter,
        final AssociadoValidate validate) {
        this.repository = repository;
        this.converter = converter;
        this.validate = validate;
    }

    public Associado criarAssociado(final AssociadoDto associadoDTO) {
        validate.validate(associadoDTO);
        validaDuplicidade(associadoDTO);
        try {
            logger.info("Salvando associado.");
            return repository.save(converter.toModel(associadoDTO));
        } catch (Exception e) {
            logger.error("Erro ao salvar associado: {}", associadoDTO.getCpf());
            throw new ErroInternoException(ERRO_CRIAR_USUARIO);
        }
    }

    public List<AssociadoDto> listarAssociados() {
        try {
            final List<AssociadoDto> listaDTO = new ArrayList<>();
            repository.findAll().forEach(a -> adicionaLista(listaDTO, a));
            logger.info("Lista de Associados encontrada.");
            return listaDTO;
        } catch (Exception e) {
            logger.error("Erro ao buscar lista de associados.");
            throw new ErroInternoException(ERRO_LISTAR_ASSOCIADOS);
        }
    }

    private void adicionaLista(final List<AssociadoDto> listaDTO, final Associado associado) {
        listaDTO.add(converter.toDTO(associado));
    }

    public Associado buscarAssociado(final String idAssociado) {
        try {
            logger.info("Buscando associado: {}", idAssociado);
            return repository.findById(idAssociado).get();
        } catch (NoSuchElementException e) {
            logger.error("Associado não encontrado: {}", idAssociado);
            throw new ObjetoNaoEncontradoException(ERRO_ASSOCIADO_NAO_ENCONTRADO);
        } catch (Exception e) {
            logger.error("Erro ao buscar associado: {}", idAssociado);
            throw new ErroInternoException(ERRO_BUSCAR_ASSOCIADO);
        }
    }

    private void validaDuplicidade(final AssociadoDto associadoDTO) {
        final Optional<Associado> associado;
        try {
            associado = repository.findByCpf(associadoDTO.getCpf());
            logger.info("Associado encontrado: {}", associadoDTO.getCpf());
        } catch (Exception e) {
            logger.error("Erro ao buscar associado: {}", associadoDTO.getCpf());
            throw new ErroInternoException(ERRO_BUSCAR_ASSOCIADO);
        }

        if (associado.isPresent()) {
            logger.error("Associado já cadastrado: {}", associadoDTO.getCpf());
            throw new ErroInternoException(ERRO_ASSOCIADO_JA_CADASTRADO);
        }
    }

}
