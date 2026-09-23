package br.csi.farmasys.controller;

import br.csi.farmasys.model.cliente.Cliente;
import br.csi.farmasys.model.cliente.DadosCliente;
import br.csi.farmasys.service.ClienteService;
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
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(this.service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna o cliente correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<Cliente> cliente(
        @Parameter(description = "ID do cliente a ser buscado", required = true)
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.service.getCliente(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente", description = "Cadastra um novo cliente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<Cliente> salvar(@RequestBody @Valid DadosCliente dados,
                                          UriComponentsBuilder uriBuilder) {
        Cliente cliente = this.service.salvar(dados);
        URI uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um cliente", description = "Atualiza um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<Cliente> atualizar(
        @Parameter(description = "ID do cliente a ser atualizado", required = true)
        @PathVariable Long id,
        @RequestBody @Valid DadosCliente dados
    ) {
        Cliente cliente = this.service.atualizar(id, dados);
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente por ID", description = "Remove um cliente do sistema pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do cliente a ser removido", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Cliente> clienteUuid(
        @Parameter(description = "UUID do cliente a ser buscado", required = true)
        @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(this.service.getClienteUUID(uuid));
    }

    @PutMapping("/uuid/{uuid}")
    @Operation(summary = "Atualizar cliente por UUID", description = "Atualiza um cliente existente localizando-o pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<Cliente> atualizarUuid(
        @Parameter(description = "UUID do cliente a ser atualizado", required = true)
        @PathVariable UUID uuid,
        @RequestBody @Valid DadosCliente dados
    ) {
        Cliente cliente = this.service.atualizarUUID(uuid, dados);
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir cliente por UUID", description = "Remove um cliente do sistema pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletarUuid(
        @Parameter(description = "UUID do cliente a ser removido", required = true)
        @PathVariable UUID uuid
    ) {
        this.service.excluirUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
