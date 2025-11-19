package com.bb.eodi.batch.legaldong.repository;

import com.bb.eodi.batch.legaldong.entity.LegalDongAdjacency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 법정동 인접정보 Repository 구현체
 */
@Repository
@RequiredArgsConstructor
public class LegalDongAdjacencyRepositoryImpl implements LegalDongAdjacencyRepository {

    private final LegalDongAdjacencyJpaRepository jpaRepository;

    @Override
    public LegalDongAdjacency save(LegalDongAdjacency entity) {
        return jpaRepository.save(entity);
    }
}
