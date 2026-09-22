package br.csi.farmasys.model.venda;

import br.csi.farmasys.model.remedio.Remedio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Item de uma venda: um remédio e a quantidade vendida")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID interno do item", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    @JsonIgnore
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "remedio_id")
    @Schema(description = "Remédio vendido. No envio, basta informar o id: {\"id\": 1}")
    private Remedio remedio;

    @Schema(description = "Quantidade vendida", example = "2")
    private Integer quantidade;

    @Column(name = "valor_unitario")
    @Schema(description = "Preço unitário no momento da venda (copiado do remédio)",
            example = "12.90", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal valorUnitario;
}
