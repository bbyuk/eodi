package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 Repository 구현체
 */
@RequiredArgsConstructor
public class RealEstateLeaseRepositoryImpl implements RealEstateLeaseRepository {

    private final EntityManager em;

    @Override
    public void saveAllChunk(List<? extends RealEstateLease> items) {
        items.stream().forEach(em::persist);
        em.flush();
        em.clear();
    }
}
