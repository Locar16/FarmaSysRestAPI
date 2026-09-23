package br.csi.farmasys.model.fornecedor;

import br.csi.farmasys.model.endereco.DadosEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de requisicao para cadastrar ou atualizar um fornecedor")
public record DadosFornecedor(

    @NotBlank(message = "razaoSocial e obrigatoria")
    @Size(max = 150)
    @Schema(description = "Razao social do fornecedor", example = "Distribuidora Farma LTDA")
    String razaoSocial,

    @NotBlank(message = "cnpj e obrigatorio")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve seguir o formato 00.000.000/0000-00")
    @Schema(description = "CNPJ do fornecedor", example = "12.345.678/0001-90")
    String cnpj,

    @Email(message = "email invalido")
    @Size(max = 100)
    @Schema(description = "E-mail do fornecedor", example = "contato@distribuidora.com")
    String email,

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve seguir o formato (00) 00000-0000")
    @Schema(description = "Telefone do fornecedor", example = "(55) 3222-1000")
    String telefone,

    @Valid
    @Schema(description = "Endereco do fornecedor")
    DadosEndereco endereco
) {
}
