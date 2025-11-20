package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.BuildingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 건물주소 Entity JpaRepository
 */
public interface BuildingAddressJpaRepository extends JpaRepository<BuildingAddress, Long> {
}
