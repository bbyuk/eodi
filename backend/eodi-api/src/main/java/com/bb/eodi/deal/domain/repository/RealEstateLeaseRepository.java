package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.dto.RegionQuery;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 부동산 임대차 실거래가 domain repository interface
 */
public interface RealEstateLeaseRepository {
    /**
     * 조회 파라미터에 해당하는 임대차 거래 정보 조회
     * @param query 조회 파라미터
     * @param pageable page 파라미터
     * @return 임대차 거래 정보 페이지
     */

    Page<RealEstateLease> findBy(RealEstateLeaseQuery query, Pageable pageable);

    /**
     * 부동산 실거래가 기준 거래가 발생한 지역 목록을 조회한다.
     * @param query 조회 쿼리 파라미터
     * @return 쿼리 파라미터에 해당하는 부동산 거래 발생 지역 데이터 목록
     */
    List<Region> findRegionsBy(RegionQuery query);
}
