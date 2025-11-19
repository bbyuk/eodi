package com.bb.eodi.batch.address.repository;

import com.bb.eodi.batch.address.entity.BuildingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 건물주소 Entity JpaRepository
 */
public interface BuildingAddressJpaRepository extends JpaRepository<BuildingAddress, Long> {
}
