package com.bb.eodi.infrastructure.deal.repository;

import com.bb.eodi.domain.deal.entity.RealEstateLease;
import com.bb.eodi.domain.deal.repository.RealEstateLeaseRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 Repository 구현체
 */
@Repository
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
