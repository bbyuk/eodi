package com.bb.eodi.deal.domain.repository;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.entity.Region;
import com.bb.eodi.deal.domain.query.RealEstateSellQuery;
import com.bb.eodi.deal.domain.query.RegionQuery;
import com.bb.eodi.deal.domain.read.RegionCandidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Stream;

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


    /**
     * 부동산 실거래가 기준 거래가 발생한 지역 목록 필터링을 위한 엔티티 조회
     * @param query 지역 조회 쿼리
     * @param pageable pageable 파라미터
     * @return 지역 조회 쿼리로 필터링된 부동산 실거래가 slice
     */
    Slice<RegionCandidate> findSliceByRegionQuery(RegionQuery query, Pageable pageable);

    /**
     * 매일 변경되는 추천지역 후보 목록 전체를 리턴한다.
     * @return 후보목록 리턴
     */
    Stream<RegionCandidate> findAllRegionCandidates();
}
