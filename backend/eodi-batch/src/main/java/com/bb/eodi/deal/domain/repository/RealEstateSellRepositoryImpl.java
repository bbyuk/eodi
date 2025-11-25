package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 부동산 매매 데이터 Repository 구현체
 */
@RequiredArgsConstructor
public class RealEstateSellRepositoryImpl implements RealEstateSellRepository {

    private final EntityManager em;

    @Override
    public void saveAllChunk(List<? extends RealEstateSell> items) {
        for (RealEstateSell item : items) {
            em.persist(item);
        }

        em.flush();
        em.clear();
    }
}
