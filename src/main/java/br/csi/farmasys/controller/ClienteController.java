package br.csi.farmasys.controller;

import br.csi.farmasys.model.cliente.Cliente;
import br.csi.farmasys.service.ClienteService;
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
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os clientes", description = "Retorna a lista completa de clientes cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public List<Cliente> listar() {
        return this.service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna o cliente correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public Cliente cliente(
        @Parameter(description = "ID do cliente a ser buscado", required = true)
        @PathVariable Long id
    ) {
        return this.service.getCliente(id);
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente", description = "Cadastra um novo cliente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public void salvar(@RequestBody Cliente cliente) {
        this.service.salvar(cliente);
    }

    @PutMapping
    @Operation(summary = "Atualizar um cliente", description = "Atualiza um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public void atualizar(@RequestBody Cliente cliente) {
        this.service.atualizar(cliente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente por ID", description = "Remove um cliente do sistema pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public void deletar(
        @Parameter(description = "ID do cliente a ser removido", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
    }

    // ==================== Endpoints por UUID (ocultando o ID interno) ====================

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar cliente por UUID", description = "Retorna o cliente correspondente ao UUID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public Cliente clienteUuid(
        @Parameter(description = "UUID do cliente a ser buscado", required = true)
        @PathVariable String uuid
    ) {
        return this.service.getClienteUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar cliente por UUID", description = "Atualiza um cliente existente localizando-o pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public void atualizarUuid(@RequestBody Cliente cliente) {
        this.service.atualizarUUID(cliente);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir cliente por UUID", description = "Remove um cliente do sistema pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public void deletarUuid(
        @Parameter(description = "UUID do cliente a ser removido", required = true)
        @PathVariable String uuid
    ) {
        this.service.deletarUUID(uuid);
    }
}
