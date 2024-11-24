package org.example.payment.adapters.gateways;

import lombok.AllArgsConstructor;
import org.example.payment.application.driven.entities.PedidoEntity;
import org.example.payment.application.driven.repositories.PedidoRepository;
import org.example.payment.core.applications.repositories.PedidoRepositoryInterface;
import org.example.payment.core.domain.Pedido;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PedidoGateway implements PedidoRepositoryInterface {

    private PedidoRepository pedidoRepository;

    @Override
    public Pedido criarPedido(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedido.getId());
        pedidoEntity.setStatusPagamento(pedido.getStatusPagamento());
        pedidoEntity.setCliente(pedido.getClienteNome());
        pedidoEntity.setValor(pedido.getValor());
        pedidoEntity.setDataCriacao(pedido.getDataCriacao());
        this.pedidoRepository.save(pedidoEntity);
        return pedido;
    }

    @Override
    public Pedido getById(UUID id) {
        Optional<PedidoEntity> entity = this.pedidoRepository.findById(id);
        if (entity.isEmpty()) {
            return null;
        }
        return this.dtoToEntidade(entity.get());
    }

    @Override
    public Pedido atualizarStatusPagamento(Pedido pedido) {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(pedido.getId());
        pedidoEntity.setStatusPagamento(pedido.getStatusPagamento());
        pedidoEntity.setCliente(pedido.getClienteNome());
        pedidoEntity.setValor(pedido.getValor());
        pedidoEntity = this.pedidoRepository.save(pedidoEntity);
        return this.dtoToEntidade(pedidoEntity);
    }

    private Pedido dtoToEntidade(PedidoEntity pedidoEntity) {
        return new Pedido(
                pedidoEntity.getId(),
                pedidoEntity.getCliente(),
                pedidoEntity.getStatusPagamento(),
                pedidoEntity.getValor(),
                pedidoEntity.getDataCriacao()
        );
    }

}
