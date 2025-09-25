package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.dto.RealEstateSellQuery;
import com.bb.eodi.deal.domain.entity.RealEstateSell;

import java.util.List;

/**
 * 부동산 매매 실거래가 repository
 */
public interface RealEstateSellRepository {
    /**
     * 부동산 실거래가 데이터 목록을 조회한다.
     * @param query 조회 쿼리 파라미터
     * @return 쿼리 파라미터에 해당하는 부동산 실거래가 데이터 목록
     */
    List<RealEstateSell> findBy(RealEstateSellQuery query);
}
