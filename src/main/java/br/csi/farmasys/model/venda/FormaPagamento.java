package br.csi.farmasys.model.venda;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Formas de pagamento aceitas")
public enum FormaPagamento {
    DINHEIRO, CARTAO, PIX
}
