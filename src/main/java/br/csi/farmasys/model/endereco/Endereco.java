package br.csi.farmasys.model.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Endereço embutido em cliente ou fornecedor")
public class Endereco {

    @Schema(description = "Complemento do endereço", example = "Apto 302")
    private String complemento;

    @Schema(description = "Bairro", example = "Centro")
    private String bairro;

    @Schema(description = "CEP", example = "97010-340")
    private String cep;

    @Schema(description = "Número", example = "123")
    private String numero;

    @Schema(description = "Cidade", example = "Santa Maria")
    private String cidade;

    @Schema(description = "Unidade federativa (UF)", example = "RS")
    private String uf;
}
