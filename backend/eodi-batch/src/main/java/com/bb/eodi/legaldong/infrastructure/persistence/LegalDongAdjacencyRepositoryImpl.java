package com.bb.eodi.legaldong.infrastructure.persistence;

import com.bb.eodi.legaldong.domain.entity.LegalDongAdjacency;
import com.bb.eodi.legaldong.infrastructure.persistence.jpa.LegalDongAdjacencyJpaRepository;
import com.bb.eodi.legaldong.domain.repository.LegalDongAdjacencyRepository;
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
