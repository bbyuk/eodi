package com.bb.eodi.deal.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 부동산 실거래가 데이터 JpaRepository
 */
public interface RealEstateSellJpaRepository extends JpaRepository<RealEstateSellJpaEntity, Long> {
}
