package org.example.payment.adapters.presenters;

import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoPresenterTest {

    @Test
    void testToObject() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente A", StatusPagamento.PAGO, 100.0, Instant.now());
        Object result = PedidoPresenter.toObject(pedido);

        assertNotNull(result);
        assertInstanceOf(Map.class, result);
        Map<String, Object> presenter = (Map<String, Object>) result;
        assertEquals(pedido.getId(), presenter.get("id"));
        assertEquals("Cliente A", presenter.get("cliente_nome"));
        assertEquals(100.0, presenter.get("valor"));
        assertEquals(true, presenter.get("pagamento_aprovado"));
    }

}
