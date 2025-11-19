package com.bb.eodi.batch.address.repository;

import com.bb.eodi.batch.address.entity.LandLotAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 지번주소 JpaRepository
 */
public interface LandLotAddressJpaRepository extends JpaRepository<LandLotAddress, Long> {
}
