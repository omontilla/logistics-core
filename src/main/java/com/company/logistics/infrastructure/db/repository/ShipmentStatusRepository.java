package com.company.logistics.infrastructure.db.repository;

import com.company.logistics.infrastructure.db.entity.ShipmentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatusEntity, Long> {
    Optional<ShipmentStatusEntity> findByCode(String code);
}
