package br.com.associado.api;

import br.com.associado.converter.AssociadoConverter;
import br.com.associado.dto.AssociadoDto;
import br.com.associado.service.AssociadoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/associado")
public class AssociadoController {

    private final AssociadoService service;
    private final AssociadoConverter converter;

    public AssociadoController(
        final AssociadoService service,
        final AssociadoConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @ApiOperation(value = "Cria um associado.", response = AssociadoDto.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Associado criado com sucesso."),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @PostMapping("/")
    public ResponseEntity<AssociadoDto> criarAssociado(@RequestBody AssociadoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.toDTO(service.criarAssociado(dto)));
    }

    @ApiOperation(value = "Lista todos os associados.", response = AssociadoDto.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<AssociadoDto>> listarAssociados() {
        return ResponseEntity.ok(service.listarAssociados());
    }

    @ApiOperation(value = "Busca associado por ID.", response = AssociadoDto.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro na requisição"),
            @ApiResponse(code = 422, message = "Erro ao processar dados de entrada"),
            @ApiResponse(code = 500, message = "Erro inesperado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDto> buscarAssociado(@PathVariable String id) {
        return ResponseEntity.ok(converter.toDTO(service.buscarAssociado(id)));
    }

}
