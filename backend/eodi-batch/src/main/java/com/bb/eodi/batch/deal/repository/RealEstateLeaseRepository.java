package com.bb.eodi.batch.deal.repository;

import com.bb.eodi.batch.deal.entity.RealEstateLease;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 Repository interface
 */
public interface RealEstateLeaseRepository {

    /**
     * chunk 배치용 메소드
     * 모든 데이터 chunk를 저장하고 1차 캐시를 flush, clear한다.
     * @param items 저장할 데이터
     */
    void saveAllChunk(List<? extends RealEstateLease> items);
}
