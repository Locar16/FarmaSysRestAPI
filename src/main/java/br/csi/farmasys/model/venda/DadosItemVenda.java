package br.csi.farmasys.model.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados de requisicao de um item da venda")
public record DadosItemVenda(

    @NotNull(message = "remedioId e obrigatorio")
    @Schema(description = "ID do remedio vendido", example = "1")
    Long remedioId,

    @NotNull(message = "quantidade e obrigatoria")
    @Schema(description = "Quantidade vendida", example = "2")
    Integer quantidade
) {
}
