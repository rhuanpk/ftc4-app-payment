package org.example.payment.core.domain;

import org.example.payment.core.applications.exception.RegraDeNegocioException;
import org.example.payment.core.domain.enums.StatusPagamento;

import java.time.Instant;
import java.util.UUID;

public class Pedido {

    private UUID id;
    private String clienteNome;
    private StatusPagamento statusPagamento;
    private double valor;
    private Instant dataCriacao;

    public Pedido(UUID id, String clienteNome, StatusPagamento statusPagamento, double valor, Instant dataCriacao) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.statusPagamento = statusPagamento;
        if (valor < 0.01) {
            throw new RegraDeNegocioException("O valor do pedido deve ser maior que 0.00");
        }
        this.valor = valor;
        this.dataCriacao = dataCriacao;
    }

    public UUID getId() {
        return this.id;
    }

    public String getClienteNome() {
        return this.clienteNome;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public double getValor() {
        return valor;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public boolean isPago() {
        return this.statusPagamento == StatusPagamento.PAGO;
    }
}
