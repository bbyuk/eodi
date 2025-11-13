package com.bb.eodi.batch.job.address.repository;

import com.bb.eodi.batch.job.address.entity.RoadNameAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 도로명주소 JpaRepository
 */
public interface RoadNameAddressJpaRepository extends JpaRepository<RoadNameAddress, Long> {
}
