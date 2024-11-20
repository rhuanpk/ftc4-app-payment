package org.example.payment.application.controllers.create;

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

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = org.mockito.MockitoAnnotations.openMocks(this);
        CreatePedidoController createPedidoController = new CreatePedidoController(this.pedidoRepositoryInterface);
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
                                .content("{\"uuid\": \""+UUID.randomUUID()+"\", \"nomeCliente\": \"Cliente\", \"valor\": \"10.98\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.cliente_nome").value("Cliente"))
                .andExpect(jsonPath("$.valor").value(10.98))
                .andExpect(jsonPath("$.pagamento_aprovado").value(false));
//                .andExpect(jsonPath("$.data_criacao").isNotEmpty())
//                .andExpect(jsonPath("$.valor").value(60.0))
//                .andExpect(jsonPath("$.itens").isArray())
//                .andExpect(jsonPath("$.itens[0].produto_nome").value("Produto"))
//                .andExpect(jsonPath("$.itens[0].valor").value(10.0))
//                .andExpect(jsonPath("$.itens[0].quantidade").value(1))
//                .andExpect(jsonPath("$.itens[1].produto_nome").value("Produto 2"))
//                .andExpect(jsonPath("$.itens[1].valor").value(25.0))
//                .andExpect(jsonPath("$.itens[1].quantidade").value(2));
    }

}
