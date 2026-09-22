package br.csi.farmasys.controller;

import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.service.RemedioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remedio")
@Tag(name = "Remédios", description = "Operações relacionadas a remédios")
public class RemedioController {

    private final RemedioService service;

    public RemedioController(RemedioService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os remédios", description = "Retorna a lista completa de remédios cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public List<Remedio> listar() {
        return this.service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar remédio por ID", description = "Retorna o remédio correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public Remedio remedio(
        @Parameter(description = "ID do remédio a ser buscado", required = true)
        @PathVariable Long id
    ) {
        return this.service.getRemedio(id);
    }

    @PostMapping
    @Operation(summary = "Criar um novo remédio", description = "Cadastra um novo remédio no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Remédio criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public void salvar(@RequestBody Remedio remedio) {
        this.service.salvar(remedio);
    }

    @PutMapping
    @Operation(summary = "Atualizar um remédio", description = "Atualiza um remédio existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public void atualizar(@RequestBody Remedio remedio) {
        this.service.atualizar(remedio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir remédio por ID", description = "Remove um remédio do sistema pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Remédio excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public void deletar(
        @Parameter(description = "ID do remédio a ser removido", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
    }

    // ==================== Endpoints por UUID (ocultando o ID interno) ====================

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar remédio por UUID", description = "Retorna o remédio correspondente ao UUID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public Remedio remedioUuid(
        @Parameter(description = "UUID do remédio a ser buscado", required = true)
        @PathVariable String uuid
    ) {
        return this.service.getRemedioUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar remédio por UUID", description = "Atualiza um remédio existente localizando-o pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public void atualizarUuid(@RequestBody Remedio remedio) {
        this.service.atualizarUUID(remedio);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir remédio por UUID", description = "Remove um remédio do sistema pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Remédio excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public void deletarUuid(
        @Parameter(description = "UUID do remédio a ser removido", required = true)
        @PathVariable String uuid
    ) {
        this.service.deletarUUID(uuid);
    }
}
