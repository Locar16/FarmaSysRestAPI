package br.csi.farmasys.controller;

import br.csi.farmasys.model.venda.ItemVenda;
import br.csi.farmasys.service.ItemVendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemvenda")
@Tag(name = "Itens de venda", description = "Consulta dos itens das vendas")
public class ItemVendaController {

    private final ItemVendaService service;

    public ItemVendaController(ItemVendaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os itens de venda", description = "Retorna todos os itens de venda registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<ItemVenda>> listar() {
        return ResponseEntity.ok(this.service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item de venda por ID", description = "Retorna o item de venda correspondente ao ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ItemVenda.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    public ResponseEntity<ItemVenda> itemVenda(
        @Parameter(description = "ID do item de venda a ser buscado", required = true)
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.service.getItemVenda(id));
    }
}
