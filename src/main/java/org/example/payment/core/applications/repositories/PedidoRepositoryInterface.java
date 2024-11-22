package org.example.payment.core.applications.repositories;

import org.example.payment.core.domain.Pedido;

import java.util.UUID;

public interface PedidoRepositoryInterface {

    public Pedido criarPedido(Pedido pedido);

    public Pedido getById(UUID id);

    public Pedido atualizarStatusPagamento(Pedido pedido);

}
