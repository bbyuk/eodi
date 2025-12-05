package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.AddressPositionFindQuery;
import com.bb.eodi.address.domain.entity.AddressPosition;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

/**
 * 주소위치정보 repository 인터페이스
 */
public interface AddressPositionRepository {

    /**
     * 파라미터 entity를 배치 insert 처리한다.
     * @param entities insert할 entity 목록
     */
    void insertBatch(List<? extends AddressPosition> entities);

    /**
     * 대상 조회 쿼리 파라미터에 해당하는 주소 위치 정보를 조회한다.
     * @param query 쿼리 파라미터
     * @return 주소 위치정보
     */
    @Cacheable(cacheNames = "addressPositionCache", key = "#query.roadNameCode + ':' + #query.legalDongCode + ':' + #query.buildingMainNo + ':' + #query.buildingSubNo + ':' + #query.isUnderground")
    Optional<AddressPosition> findAddressPosition(AddressPositionFindQuery query);
}
