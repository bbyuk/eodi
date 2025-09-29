package com.bb.eodi.deal.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 부동산 임대차 실거래가 데이터 JpaRepository */
public interface RealEstateLeaseJpaRepository extends JpaRepository<RealEstateLeaseJpaEntity, Long> {
}
