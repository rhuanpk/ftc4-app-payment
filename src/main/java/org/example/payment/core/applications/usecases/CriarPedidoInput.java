package org.example.payment.core.applications.usecases;

import java.util.UUID;

public record CriarPedidoInput(UUID uuid, String nomeCliente, double valor) {
}
