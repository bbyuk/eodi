package com.bb.eodi.address.infrastructure.persistence.jpa;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 지번주소 JpaRepository
 */
public interface LandLotAddressJpaRepository extends JpaRepository<LandLotAddress, Long> {
}
