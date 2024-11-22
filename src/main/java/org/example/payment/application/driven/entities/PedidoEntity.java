package org.example.payment.application.driven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.payment.core.domain.enums.StatusPagamento;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TB_PEDIDO")
public class PedidoEntity {

    @Id
    private UUID id;

    private double valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    private String cliente;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant dataCriacao;

}
