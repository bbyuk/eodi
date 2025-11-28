package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.BuildingAddressFindQuery;
import com.bb.eodi.address.domain.entity.BuildingAddress;

import java.util.List;
import java.util.Optional;

/**
 * 건물주소 Repository 인터페이스
 */
public interface BuildingAddressRepository {

    /**
     * 배치 insert 처리한다.
     * @param entities insert 대상 entity 목록
     */
    void batchInsert(List<? extends BuildingAddress> entities);

    /**
     * 조회 쿼리 파라미터에 해당하는 건물주소를 찾아 리턴한다.
     * @param query 조회 쿼리 파라미터
     * @return 건물주소 목록
     */
    List<BuildingAddress> findBuildingAddress(BuildingAddressFindQuery query);
}
