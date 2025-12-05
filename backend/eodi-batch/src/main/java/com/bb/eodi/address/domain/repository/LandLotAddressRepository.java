package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.LandLotAddressFindQuery;
import com.bb.eodi.address.domain.entity.LandLotAddress;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 지번주소 Repository
 */
public interface LandLotAddressRepository {
    /**
     * 대상 entity 목록을 batch insert한다.
     * @param entities insert 대상 entity chunk
     */
    void insertBatch(List<? extends LandLotAddress> entities);

    /**
     * 조회 쿼리 파라미터에 해당하는 지번주소를 찾아 리턴한다.
     * @param query 조회 쿼리 파라미터
     * @return 지번주소 목록
     */
    @Cacheable(cacheNames="landLotAddressCache", key = "#query.legalDongCode + ':' + #query.landLotMainNo + ':' + #query.landLotSubNo")
    List<LandLotAddress> findLandLotAddress(LandLotAddressFindQuery query);
}
