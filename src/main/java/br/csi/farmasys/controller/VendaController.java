package br.csi.farmasys.controller;

import br.csi.farmasys.model.venda.DadosAtualizacaoVenda;
import br.csi.farmasys.model.venda.DadosCadastroVenda;
import br.csi.farmasys.model.venda.Venda;
import br.csi.farmasys.service.VendaService;
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
@RequestMapping("/venda")
@Tag(name = "Vendas", description = "Registro de vendas com baixa automática de estoque")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as vendas", description = "Retorna todas as vendas com cliente e itens")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<Venda>> listar() {
        return ResponseEntity.ok(this.service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda por ID", description = "Retorna a venda correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public ResponseEntity<Venda> venda(
        @Parameter(description = "ID da venda a ser buscada", required = true)
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.service.getVenda(id));
    }

    @PostMapping
    @Operation(summary = "Registrar uma nova venda",
        description = "Registra a venda, verifica e baixa o estoque de cada remédio e calcula o total. "
            + "Informe clienteId (opcional) e os itens com remedioId e quantidade.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venda registrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou estoque insuficiente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cliente ou remédio não encontrado", content = @Content)
    })
    public ResponseEntity<Venda> salvar(@RequestBody @Valid DadosCadastroVenda dados,
                                        UriComponentsBuilder uriBuilder) {
        Venda venda = this.service.salvar(dados);
        URI uri = uriBuilder.path("/venda/{id}").buildAndExpand(venda.getId()).toUri();
        return ResponseEntity.created(uri).body(venda);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma venda",
        description = "Altera apenas o cliente e a forma de pagamento. Os itens não são alterados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public ResponseEntity<Venda> atualizar(
        @Parameter(description = "ID da venda a ser atualizada", required = true)
        @PathVariable Long id,
        @RequestBody @Valid DadosAtualizacaoVenda dados
    ) {
        Venda venda = this.service.atualizar(id, dados);
        return ResponseEntity.ok().body(venda);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir venda por ID", description = "Remove a venda e devolve os itens ao estoque")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID da venda a ser removida", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Endpoints por UUID (ocultando o ID interno) ====================

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar venda por UUID", description = "Retorna a venda correspondente ao UUID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public ResponseEntity<Venda> vendaUuid(
        @Parameter(description = "UUID da venda a ser buscada", required = true)
        @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(this.service.getVendaUUID(uuid));
    }

    @PutMapping("/uuid/{uuid}")
    @Operation(summary = "Atualizar venda por UUID",
        description = "Altera cliente e forma de pagamento localizando a venda pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public ResponseEntity<Venda> atualizarUuid(
        @Parameter(description = "UUID da venda a ser atualizada", required = true)
        @PathVariable UUID uuid,
        @RequestBody @Valid DadosAtualizacaoVenda dados
    ) {
        Venda venda = this.service.atualizarUUID(uuid, dados);
        return ResponseEntity.ok().body(venda);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir venda por UUID", description = "Remove a venda pelo UUID e devolve os itens ao estoque")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deletarUuid(
        @Parameter(description = "UUID da venda a ser removida", required = true)
        @PathVariable UUID uuid
    ) {
        this.service.excluirUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
