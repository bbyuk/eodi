package com.bb.eodi.address.infrastructure.persistence.jpa;

import com.bb.eodi.address.domain.entity.AddressPosition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주소 좌표 정보 JpaRepository
 */
public interface AddressPositionJpaRepository extends JpaRepository<AddressPosition, Long> {
}
