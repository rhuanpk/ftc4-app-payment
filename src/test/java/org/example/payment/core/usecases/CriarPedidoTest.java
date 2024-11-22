package org.example.payment.core.usecases;

import org.example.payment.core.applications.exception.RegraDeNegocioException;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.applications.usecases.CriarPedido;
import org.example.payment.core.applications.usecases.CriarPedidoInput;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

class CriarPedidoTest {

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarUmPedido() {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPagamento.AGUARDANDO, 10, Instant.now());
        when(this.pedidoRepositoryInterface.criarPedido(any(Pedido.class))).thenReturn(pedido);

        CriarPedido criarPedido = new CriarPedido(this.pedidoRepositoryInterface);
        CriarPedidoInput input = new CriarPedidoInput(UUID.randomUUID(), "Cliente", 10.00);
        Pedido pedidoSalvo = criarPedido.execute(input);
        assertThat(pedidoSalvo).isNotNull();
        assertThat(pedidoSalvo.getId()).isNotNull();
        assertThat(pedidoSalvo.getClienteNome()).isEqualTo("Cliente");
        assertThat(pedidoSalvo.getStatusPagamento()).isEqualTo(StatusPagamento.AGUARDANDO);
        assertThat(pedidoSalvo.getValor()).isEqualTo(10.00);
        verify(this.pedidoRepositoryInterface, times(1)).criarPedido(any(Pedido.class));
    }

    @Test
    void naoDevePermitirCriarPedidoComComValorZerado() {
        CriarPedido criarPedido = new CriarPedido(this.pedidoRepositoryInterface);
        CriarPedidoInput input = new CriarPedidoInput(UUID.randomUUID(), "Cliente", 0.00);
        assertThatThrownBy(() -> criarPedido.execute(input))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("O valor do pedido deve ser maior que 0.00");
    }

}
