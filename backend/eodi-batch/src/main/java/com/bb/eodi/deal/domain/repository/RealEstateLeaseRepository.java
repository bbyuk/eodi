package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateLease;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 Repository interface
 */
public interface RealEstateLeaseRepository {

    /**
     * chunk 배치용 메소드
     * @param items 저장할 데이터
     */
    void saveAllChunk(List<? extends RealEstateLease> items);

    /**
     * 실거래 데이터 위치 정보를 배치 업데이트 한다.
     * @param items chunk 데이터
     */
    void batchUpdatePosition(List<? extends RealEstateLease> items);
}
