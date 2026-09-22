package br.csi.farmasys.controller;

import br.csi.farmasys.model.fornecedor.Fornecedor;
import br.csi.farmasys.service.FornecedorService;
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
@RequestMapping("/fornecedor")
@Tag(name = "Fornecedores", description = "Operações relacionadas a fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os fornecedores", description = "Retorna a lista completa de fornecedores cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public List<Fornecedor> listar() {
        return this.service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna o fornecedor correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Fornecedor.class))),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public Fornecedor fornecedor(
        @Parameter(description = "ID do fornecedor a ser buscado", required = true)
        @PathVariable Long id
    ) {
        return this.service.getFornecedor(id);
    }

    @PostMapping
    @Operation(summary = "Criar um novo fornecedor", description = "Cadastra um novo fornecedor no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Fornecedor.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public void salvar(@RequestBody Fornecedor fornecedor) {
        this.service.salvar(fornecedor);
    }

    @PutMapping
    @Operation(summary = "Atualizar um fornecedor", description = "Atualiza um fornecedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public void atualizar(@RequestBody Fornecedor fornecedor) {
        this.service.atualizar(fornecedor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir fornecedor por ID", description = "Remove um fornecedor do sistema pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public void deletar(
        @Parameter(description = "ID do fornecedor a ser removido", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
    }

    // ==================== Endpoints por UUID (ocultando o ID interno) ====================

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar fornecedor por UUID", description = "Retorna o fornecedor correspondente ao UUID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Fornecedor.class))),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public Fornecedor fornecedorUuid(
        @Parameter(description = "UUID do fornecedor a ser buscado", required = true)
        @PathVariable String uuid
    ) {
        return this.service.getFornecedorUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar fornecedor por UUID", description = "Atualiza um fornecedor existente localizando-o pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public void atualizarUuid(@RequestBody Fornecedor fornecedor) {
        this.service.atualizarUUID(fornecedor);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir fornecedor por UUID", description = "Remove um fornecedor do sistema pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fornecedor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado", content = @Content)
    })
    public void deletarUuid(
        @Parameter(description = "UUID do fornecedor a ser removido", required = true)
        @PathVariable String uuid
    ) {
        this.service.deletarUUID(uuid);
    }
}
