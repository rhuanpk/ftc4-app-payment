package org.example.payment.application.controllers.create.requests;

import java.util.UUID;

public record PedidoCreateRequest(UUID uuid, String nomeCliente, double valor) {

}