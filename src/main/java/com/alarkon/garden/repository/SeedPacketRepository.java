package com.alarkon.garden.repository;

import com.alarkon.garden.model.SeedPacket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeedPacketRepository extends JpaRepository<SeedPacket, Long> {
    List<SeedPacket> findByOwnerId(Long ownerId);
    List<SeedPacket> findByOwnerIdAndRemainingSeedsGreaterThan(Long ownerId, int seeds);
    List<SeedPacket> findByProductId(Long productId);
}
