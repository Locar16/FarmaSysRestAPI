package br.csi.farmasys.model.venda;

import br.csi.farmasys.model.cliente.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vendas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa uma venda realizada pela farmácia")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID interno da venda", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID público da venda", example = "edddd513-9ecd-45ac-8387-16256eae723a")
    private UUID uuid;

    @Column(name = "data_hora")
    @Schema(description = "Data e hora da venda (preenchida pelo sistema)",
            example = "2026-09-22T14:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataHora;

    @Column(name = "valor_total")
    @Schema(description = "Valor total da venda (calculado pelo sistema)",
            example = "25.80", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    @Schema(description = "Forma de pagamento", example = "PIX")
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @Schema(description = "Cliente da venda (opcional). No envio, basta informar o id: {\"id\": 1}")
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Itens da venda")
    private List<ItemVenda> itens = new ArrayList<>();
}
