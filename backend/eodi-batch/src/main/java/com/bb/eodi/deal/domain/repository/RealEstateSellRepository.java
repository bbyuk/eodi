package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateSell;

import java.util.List;

/**
 * 부동산 매매 데이터 Repository 인터페이스
 */
public interface RealEstateSellRepository {
    /**
     * chunk 배치용 메소드
     * 모든 데이터 chunk를 저장하고 1차 캐시를 flush, clear한다.
     * @param items 저장할 데이터
     */
    void saveAllChunk(List<? extends RealEstateSell> items);
}
