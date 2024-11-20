package org.example.payment.core.entities;

import org.example.payment.core.applications.exception.RegraDeNegocioException;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class PedidoTest {

    @ParameterizedTest
    @CsvSource({
            "AGUARDANDO, false",
            "PAGO, true"
    })
    void deveVerificarOMetodoIsPago(StatusPagamento statusPagamento, boolean pago) {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", statusPagamento, 10, Instant.now());
        assertThat(pedido.isPago()).isEqualTo(pago);
    }

    @Test
    void naoDevePermitirUmPedidoComOValorZerado() {
        assertThatThrownBy(() -> new Pedido(UUID.randomUUID(), "Cliente", StatusPagamento.AGUARDANDO, 0, Instant.now()))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("O valor do pedido deve ser maior que 0.00");
    }
}
