package com.bb.eodi.batch.legaldong.repository;

import com.bb.eodi.batch.legaldong.entity.LegalDongAdjacency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 법정동 인접정보 Spring Data JPA Repository
 */
public interface LegalDongAdjacencyJpaRepository extends JpaRepository<LegalDongAdjacency, Long> {
}
