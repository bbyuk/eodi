package com.bb.eodi.infrastructure.deal.repository;

import com.bb.eodi.domain.deal.entity.RealEstateSell;
import com.bb.eodi.domain.deal.repository.RealEstateSellRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 부동산 매매 데이터 Repository 구현체
 */
@Repository
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
