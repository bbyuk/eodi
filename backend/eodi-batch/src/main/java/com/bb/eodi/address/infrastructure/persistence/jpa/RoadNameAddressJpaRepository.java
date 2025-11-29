package com.bb.eodi.address.infrastructure.persistence.jpa;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 도로명주소 JpaRepository
 */
public interface RoadNameAddressJpaRepository extends JpaRepository<RoadNameAddress, Long> {
}
