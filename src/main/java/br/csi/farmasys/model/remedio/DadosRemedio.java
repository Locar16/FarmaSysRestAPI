package br.csi.farmasys.model.remedio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados de requisicao para cadastrar ou atualizar um remedio")
public record DadosRemedio(

    @NotBlank(message = "nome e obrigatorio")
    @Size(max = 100)
    @Schema(description = "Nome do remedio", example = "Dipirona 500mg")
    String nome,

    @Size(max = 100)
    @Schema(description = "Principio ativo do remedio", example = "Dipirona sodica")
    String principioAtivo,

    @NotNull(message = "preco e obrigatorio")
    @Schema(description = "Preco do remedio", example = "12.90")
    BigDecimal preco,

    @NotNull(message = "quantidadeEstoque e obrigatoria")
    @Schema(description = "Quantidade em estoque", example = "150")
    Integer quantidadeEstoque,

    @NotNull(message = "necessitaReceita e obrigatorio")
    @Schema(description = "Indica se o remedio necessita receita medica", example = "false")
    Boolean necessitaReceita,

    @Schema(description = "ID do fornecedor do remedio (opcional)", example = "1")
    Long fornecedorId
) {
}
