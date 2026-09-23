package br.csi.farmasys.model.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de endereco (usado em cliente e fornecedor)")
public record DadosEndereco(

    @Size(max = 100)
    @Schema(description = "Complemento do endereco", example = "Apto 302")
    String complemento,

    @Size(max = 100)
    @Schema(description = "Bairro", example = "Centro")
    String bairro,

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve seguir o formato 00000-000")
    @Schema(description = "CEP", example = "97010-340")
    String cep,

    @Size(max = 20)
    @Schema(description = "Numero", example = "123")
    String numero,

    @Size(max = 100)
    @Schema(description = "Cidade", example = "Santa Maria")
    String cidade,

    @Pattern(regexp = "[A-Z]{2}", message = "UF deve ter 2 letras maiusculas")
    @Schema(description = "Unidade federativa (UF)", example = "RS")
    String uf
) {
}
