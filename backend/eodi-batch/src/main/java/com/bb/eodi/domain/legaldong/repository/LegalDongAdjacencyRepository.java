package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDongAdjacency;

/**
 * 법정동 인접정보 repository interface
 */
public interface LegalDongAdjacencyRepository {
    /**
     * 법정동 인접정보 entity를 저장하고 리턴한다.
     * @param entity 저장할 entity
     * @return 저장된 entity
     */
    LegalDongAdjacency save(LegalDongAdjacency entity);
}
