package org.example.payment.application.driven.repositories;

import org.example.payment.application.driven.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<PedidoEntity, UUID> {
    Optional<PedidoEntity> findById(UUID id);
}

