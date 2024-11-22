package org.example.payment.core.applications.usecases;

import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;

import java.time.Instant;

public class CriarPedido {

    private final PedidoRepositoryInterface pedidoRepository;

    public CriarPedido(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(CriarPedidoInput input) {
        Pedido pedido = new Pedido(input.uuid(), input.nomeCliente(), StatusPagamento.AGUARDANDO, input.valor(), Instant.now());
        this.pedidoRepository.criarPedido(pedido);
        return pedido;
    }

}
