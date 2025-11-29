package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.AddressPosition;

import java.util.List;

/**
 * 주소위치정보 repository 인터페이스
 */
public interface AddressPositionRepository {

    /**
     * 파라미터 entity를 배치 insert 처리한다.
     * @param entities insert할 entity 목록
     */
    void insertBatch(List<? extends AddressPosition> entities);
}
