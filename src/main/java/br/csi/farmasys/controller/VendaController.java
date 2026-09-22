package br.csi.farmasys.controller;

import br.csi.farmasys.model.venda.Venda;
import br.csi.farmasys.service.VendaService;
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
    public List<Venda> listar() {
        return this.service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda por ID", description = "Retorna a venda correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public Venda venda(
        @Parameter(description = "ID da venda a ser buscada", required = true)
        @PathVariable Long id
    ) {
        return this.service.getVenda(id);
    }

    @PostMapping
    @Operation(summary = "Registrar uma nova venda",
        description = "Registra a venda, verifica e baixa o estoque de cada remédio e calcula o total. "
            + "Informe cliente (opcional) e remédios apenas pelo id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venda registrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Venda.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou estoque insuficiente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cliente ou remédio não encontrado", content = @Content)
    })
    public void salvar(@RequestBody Venda venda) {
        this.service.salvar(venda);
    }

    @PutMapping
    @Operation(summary = "Atualizar uma venda",
        description = "Altera apenas o cliente e a forma de pagamento. Os itens não são alterados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public void atualizar(@RequestBody Venda venda) {
        this.service.atualizar(venda);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir venda por ID", description = "Remove a venda e devolve os itens ao estoque")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public void deletar(
        @Parameter(description = "ID da venda a ser removida", required = true)
        @PathVariable Long id
    ) {
        this.service.excluir(id);
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
    public Venda vendaUuid(
        @Parameter(description = "UUID da venda a ser buscada", required = true)
        @PathVariable String uuid
    ) {
        return this.service.getVendaUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar venda por UUID",
        description = "Altera cliente e forma de pagamento localizando a venda pelo UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public void atualizarUuid(@RequestBody Venda venda) {
        this.service.atualizarUUID(venda);
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Excluir venda por UUID", description = "Remove a venda pelo UUID e devolve os itens ao estoque")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content)
    })
    public void deletarUuid(
        @Parameter(description = "UUID da venda a ser removida", required = true)
        @PathVariable String uuid
    ) {
        this.service.deletarUUID(uuid);
    }
}
