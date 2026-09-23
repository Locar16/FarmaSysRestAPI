package br.csi.farmasys.model.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados de requisicao para atualizar uma venda (itens nao sao alterados)")
public record DadosAtualizacaoVenda(

    @Schema(description = "ID do cliente da venda (opcional)", example = "1")
    Long clienteId,

    @NotNull(message = "formaPagamento e obrigatoria")
    @Schema(description = "Forma de pagamento", example = "PIX")
    FormaPagamento formaPagamento
) {
}
