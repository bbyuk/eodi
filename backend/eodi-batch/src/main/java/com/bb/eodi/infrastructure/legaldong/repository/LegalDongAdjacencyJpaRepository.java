package com.bb.eodi.infrastructure.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDongAdjacency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 법정동 인접정보 Spring Data JPA Repository
 */
public interface LegalDongAdjacencyJpaRepository extends JpaRepository<LegalDongAdjacency, Long> {
}
