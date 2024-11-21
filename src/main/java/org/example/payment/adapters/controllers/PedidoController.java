package org.example.payment.adapters.controllers;

import org.example.payment.adapters.presenters.PedidoPresenter;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.applications.usecases.*;
import org.example.payment.core.applications.usecases.CriarPedido;
import org.example.payment.core.applications.usecases.CriarPedidoInput;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PedidoController {

    private final PedidoRepositoryInterface pedidoRepository;

    public PedidoController(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Map<String, Object> criarPedido(CriarPedidoInput input) {
        CriarPedido criarPedido = new CriarPedido(this.pedidoRepository);
        return PedidoPresenter.toObject(criarPedido.execute(input));
    }

    public Map<String, Object> atualizarStatusPagamento(UUID id, StatusPagamento statusPagamento) {
        AtualizarStatusPagamento atualizarStatusPagamento = new AtualizarStatusPagamento(this.pedidoRepository);
        return PedidoPresenter.toObject(atualizarStatusPagamento.execute(id, statusPagamento));
    }

}
