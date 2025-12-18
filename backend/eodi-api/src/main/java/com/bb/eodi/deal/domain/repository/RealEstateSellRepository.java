package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.query.RealEstateSellQuery;
import com.bb.eodi.deal.domain.query.RegionQuery;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<RealEstateSell> findBy(RealEstateSellQuery query, Pageable pageable);

    /**
     * 부동산 실거래가 기준 거래가 발생한 지역 목록을 조회한다.
     * @param query 조회 쿼리 파라미터
     * @return 쿼리 파라미터에 해당하는 부동산 거래 발생 지역 데이터 목록
     */
    List<Region> findRegionsBy(RegionQuery query);

}
