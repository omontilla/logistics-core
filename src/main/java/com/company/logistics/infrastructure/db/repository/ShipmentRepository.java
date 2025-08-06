package com.company.logistics.infrastructure.db.repository;

import com.company.logistics.infrastructure.db.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> {

    @Query("SELECT s FROM ShipmentEntity s " +
            "JOIN s.status st " +
            "WHERE (CAST(:status AS string) IS NULL OR st.code = :status) AND " +
            "(CAST(:destination AS string) IS NULL OR s.destination = :destination) AND " +
            "(CAST(:creationDate AS timestamp) IS NULL OR s.creationDate >= :creationDate)")
    List<ShipmentEntity> findAllWithFilters(
            @Param("status") String status,
            @Param("destination") String destination,
            @Param("creationDate") LocalDateTime creationDate);
}
