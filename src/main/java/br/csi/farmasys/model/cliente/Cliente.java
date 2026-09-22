package br.csi.farmasys.model.cliente;

import br.csi.farmasys.model.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um cliente no sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID interno do cliente", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID público do cliente", example = "edddd513-9ecd-45ac-8387-16256eae723a")
    private UUID uuid;

    @Schema(description = "Nome do cliente", example = "Maria da Silva")
    private String nome;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "E-mail do cliente", example = "maria@email.com")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(55) 99999-0000")
    private String telefone;

    @Column(name = "data_nascimento")
    @Schema(description = "Data de nascimento", example = "1990-05-20")
    private LocalDate dataNascimento;

    @Embedded
    private Endereco endereco;
}
