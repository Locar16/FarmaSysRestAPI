package br.csi.farmasys.model.venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Dados de requisicao para registrar uma venda")
public record DadosCadastroVenda(

    @Schema(description = "ID do cliente da venda (opcional)", example = "1")
    Long clienteId,

    @NotNull(message = "formaPagamento e obrigatoria")
    @Schema(description = "Forma de pagamento", example = "PIX")
    FormaPagamento formaPagamento,

    @NotNull(message = "itens e obrigatorio")
    @Size(min = 1, message = "a venda deve ter pelo menos um item")
    @Valid
    @Schema(description = "Itens da venda")
    List<DadosItemVenda> itens
) {
}
