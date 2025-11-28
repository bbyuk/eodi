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

    List<BuildingAddress> findBuildingAddress(BuildingAddressFindQuery query);
}
