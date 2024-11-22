package org.example.payment.core.applications.usecases;

import org.example.payment.core.applications.exception.EntityNotFoundException;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.domain.Pedido;
import org.example.payment.core.domain.enums.StatusPagamento;

import java.util.UUID;

public class AtualizarStatusPagamento {

    private final PedidoRepositoryInterface pedidoRepository;

    public AtualizarStatusPagamento(PedidoRepositoryInterface pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(UUID id, StatusPagamento statusPagamento) {
        Pedido pedido = this.pedidoRepository.getById(id);
        if (pedido == null) {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
        pedido.setStatusPagamento(statusPagamento);
        return this.pedidoRepository.atualizarStatusPagamento(pedido);
    }

}
