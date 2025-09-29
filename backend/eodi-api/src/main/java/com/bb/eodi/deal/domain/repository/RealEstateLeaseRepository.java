package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.dto.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
