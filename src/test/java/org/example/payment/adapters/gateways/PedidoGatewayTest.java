package org.example.payment.adapters.gateways;

import org.example.payment.adapters.gateways.PedidoGateway;
import org.example.payment.application.driven.entities.PedidoEntity;
import org.example.payment.application.driven.repositories.PedidoRepository;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoGatewayTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoGateway pedidoGateway;

    Pedido pedido;

    PedidoEntity pedidoEntity;

    UUID uuid;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);

        this.uuid = UUID.randomUUID();

        this.pedido = new Pedido(this.uuid, "Cliente A", StatusPagamento.AGUARDANDO, 100.0, Instant.now());

        this.pedidoEntity = new PedidoEntity();
        this.pedidoEntity.setId(this.uuid);
        this.pedidoEntity.setCliente(this.pedido.getClienteNome());
        this.pedidoEntity.setStatusPagamento(this.pedido.getStatusPagamento());
        this.pedidoEntity.setValor(this.pedido.getValor());
        this.pedidoEntity.setDataCriacao(this.pedido.getDataCriacao());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarUmPedido() {
        this.pedidoGateway.criarPedido(this.pedido);
        verify(this.pedidoRepository, times(1)).save(this.pedidoEntity);
    }

    @Test
    void deveRetornarUmPedidoPeloUuid() {
        when(this.pedidoRepository.findById(this.uuid)).thenReturn(Optional.of(this.pedidoEntity));
        Pedido pedido = this.pedidoGateway.getById(this.uuid);
        assertNotNull(pedido);
        assertEquals(pedido.getId(), this.pedidoEntity.getId());
        assertEquals(pedido.getClienteNome(), this.pedidoEntity.getCliente());
        assertEquals(pedido.getStatusPagamento(), this.pedidoEntity.getStatusPagamento());
        assertEquals(pedido.getValor(), this.pedidoEntity.getValor());
        assertEquals(pedido.getDataCriacao(), this.pedidoEntity.getDataCriacao());
    }

    @Test
    void naoDeveRetornarUmPedidoPoisOUuidEhInvalido() {
        when(this.pedidoRepository.findById(this.uuid)).thenReturn(Optional.empty());
        Pedido pedido = this.pedidoGateway.getById(this.uuid);
        assertThat(pedido).isNull();
    }

    @Test
    void deveAtualizarOStatusDoPagamentoDoPedido() {
        when(this.pedidoRepository.save(any(PedidoEntity.class))).thenReturn(this.pedidoEntity);
        this.pedido.setStatusPagamento(StatusPagamento.PAGO);
        Pedido pedido = this.pedidoGateway.atualizarStatusPagamento(this.pedido);
        assertNotNull(pedido);
        assertEquals(pedido.getId(), this.pedidoEntity.getId());
        assertEquals(pedido.getClienteNome(), this.pedidoEntity.getCliente());
        assertEquals(pedido.getStatusPagamento(), this.pedidoEntity.getStatusPagamento());
        assertEquals(pedido.getValor(), this.pedidoEntity.getValor());
        assertEquals(pedido.getDataCriacao(), this.pedidoEntity.getDataCriacao());
        verify(this.pedidoRepository, times(1)).save(any(PedidoEntity.class));
    }

}
