package com.bb.eodi.legaldong.infrastructure.persistence.jpa;

import com.bb.eodi.legaldong.domain.entity.LegalDongAdjacency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 법정동 인접정보 Spring Data JPA Repository
 */
public interface LegalDongAdjacencyJpaRepository extends JpaRepository<LegalDongAdjacency, Long> {
}
