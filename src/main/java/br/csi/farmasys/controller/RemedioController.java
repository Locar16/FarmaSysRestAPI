package br.csi.farmasys.controller;

import br.csi.farmasys.model.remedio.DadosRemedio;
import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.service.RemedioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<Remedio>> listar() {
        return ResponseEntity.ok(this.service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar remédio por ID", description = "Retorna o remédio correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public ResponseEntity<Remedio> remedio(
        @Parameter(description = "ID do remédio a ser buscado", required = true)
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.service.getRemedio(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo remédio", description = "Cadastra um novo remédio no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Remédio criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public ResponseEntity<Remedio> salvar(@RequestBody @Valid DadosRemedio dados,
                                          UriComponentsBuilder uriBuilder) {
        Remedio remedio = this.service.salvar(dados);
        URI uri = uriBuilder.path("/remedio/{id}").buildAndExpand(remedio.getId()).toUri();
        return ResponseEntity.created(uri).body(remedio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um remédio", description = "Atualiza um remédio existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public ResponseEntity<Remedio> atualizar(
        @Parameter(description = "ID do remédio a ser atualizado", required = true)
        @PathVariable Long id,
        @RequestBody @Valid DadosRemedio dados
    ) {
        Remedio remedio = this.service.atualizar(id, dados);
        return ResponseEntity.ok().body(remedio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir remédio por ID", description = "Remove um remédio do sistema pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Remédio excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do remédio a ser removido", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Remedio> remedioUuid(
        @Parameter(description = "UUID do remédio a ser buscado", required = true)
        @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(this.service.getRemedioUUID(uuid));
    }

    @PutMapping("/uuid/{uuid}")
    @Operation(summary = "Atualizar remédio por UUID", description = "Atualiza um remédio existente localizando-o pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Remédio atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Remedio.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public ResponseEntity<Remedio> atualizarUuid(
        @Parameter(description = "UUID do remédio a ser atualizado", required = true)
        @PathVariable UUID uuid,
        @RequestBody @Valid DadosRemedio dados
    ) {
        Remedio remedio = this.service.atualizarUUID(uuid, dados);
        return ResponseEntity.ok().body(remedio);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir remédio por UUID", description = "Remove um remédio do sistema pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Remédio excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Remédio não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletarUuid(
        @Parameter(description = "UUID do remédio a ser removido", required = true)
        @PathVariable UUID uuid
    ) {
        this.service.excluirUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
