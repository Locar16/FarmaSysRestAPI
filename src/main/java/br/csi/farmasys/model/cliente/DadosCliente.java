package br.csi.farmasys.model.cliente;

import br.csi.farmasys.model.endereco.DadosEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Dados de requisicao para cadastrar ou atualizar um cliente")
public record DadosCliente(

    @NotBlank(message = "nome e obrigatorio")
    @Size(max = 100)
    @Schema(description = "Nome do cliente", example = "Maria da Silva")
    String nome,

    @NotBlank(message = "cpf e obrigatorio")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve seguir o formato 000.000.000-00")
    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    String cpf,

    @Email(message = "email invalido")
    @Size(max = 100)
    @Schema(description = "E-mail do cliente", example = "maria@email.com")
    String email,

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Telefone deve seguir o formato (00) 00000-0000")
    @Schema(description = "Telefone do cliente", example = "(55) 99999-0000")
    String telefone,

    @Schema(description = "Data de nascimento", example = "1990-05-20")
    LocalDate dataNascimento,

    @Valid
    @Schema(description = "Endereco do cliente")
    DadosEndereco endereco
) {
}
