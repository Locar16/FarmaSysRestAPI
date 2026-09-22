package br.csi.farmasys.model.fornecedor;

import br.csi.farmasys.model.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Entity
@Table(name = "fornecedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um fornecedor no sistema")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID interno do fornecedor", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID público do fornecedor", example = "edddd513-9ecd-45ac-8387-16256eae723a")
    private UUID uuid;

    @Column(name = "razao_social")
    @Schema(description = "Razão social do fornecedor", example = "Distribuidora Farma LTDA")
    private String razaoSocial;

    @Schema(description = "CNPJ do fornecedor", example = "12.345.678/0001-90")
    private String cnpj;

    @Schema(description = "E-mail do fornecedor", example = "contato@distribuidora.com")
    private String email;

    @Schema(description = "Telefone do fornecedor", example = "(55) 3222-1000")
    private String telefone;

    @Embedded
    private Endereco endereco;
}
