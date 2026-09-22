package br.csi.farmasys.model.remedio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "remedios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um remédio no sistema")
public class Remedio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID interno do remédio", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID público do remédio", example = "edddd513-9ecd-45ac-8387-16256eae723a")
    private UUID uuid;

    @Schema(description = "Nome do remédio", example = "Dipirona 500mg")
    private String nome;

    @Column(name = "principio_ativo")
    @Schema(description = "Princípio ativo do remédio", example = "Dipirona sódica")
    private String principioAtivo;

    @Schema(description = "Preço do remédio", example = "12.90")
    private BigDecimal preco;

    @Column(name = "quantidade_estoque")
    @Schema(description = "Quantidade em estoque", example = "150")
    private Integer quantidadeEstoque;

    @Column(name = "necessita_receita")
    @Schema(description = "Indica se o remédio necessita receita médica", example = "false")
    private Boolean necessitaReceita;
}
