package org.example.payment.adapters.presenters;

import org.example.payment.core.domain.Pedido;

import java.util.HashMap;
import java.util.Map;

public class PedidoPresenter {

    public static Object toObject(Pedido pedido) {
        Map<String, Object> presenter = new HashMap<>();
        presenter.put("id", pedido.getId());
        presenter.put("cliente_nome", pedido.getClienteNome());
        presenter.put("valor", pedido.getValor());
        presenter.put("pagamento_aprovado", pedido.isPago());
        return presenter;
    }

}
