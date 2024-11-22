package org.example.payment.application.controllers.create;

import org.example.payment.adapters.services.RequestInterface;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CreatePedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoRepositoryInterface pedidoRepositoryInterface;

    @Mock
    private RequestInterface requestInterface;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        CreatePedidoController createPedidoController = new CreatePedidoController(this.pedidoRepositoryInterface, this.requestInterface);
        this.mockMvc = MockMvcBuilders.standaloneSetup(createPedidoController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarUmPedido() throws Exception {
        Pedido pedido = new Pedido(UUID.randomUUID(), "Cliente", StatusPagamento.AGUARDANDO, 10, Instant.now());
        when(this.pedidoRepositoryInterface.criarPedido(any(Pedido.class))).thenReturn(pedido);

        when(this.pedidoRepositoryInterface.getById(any(UUID.class))).thenReturn(pedido);
        when(this.pedidoRepositoryInterface.atualizarStatusPagamento(any(Pedido.class))).thenReturn(pedido);

        this.mockMvc.perform(
                        post("/pedidos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"uuid\": \"" + UUID.randomUUID() + "\", \"nomeCliente\": \"Cliente\", \"valor\": \"10.98\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.cliente_nome").value("Cliente"))
                .andExpect(jsonPath("$.valor").value(10.98))
                .andExpect(jsonPath("$.pagamento_aprovado").value(false));
    }

}
