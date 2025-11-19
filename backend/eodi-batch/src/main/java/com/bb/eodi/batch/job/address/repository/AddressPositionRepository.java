package com.bb.eodi.batch.job.address.repository;

import com.bb.eodi.batch.job.address.entity.AddressPosition;

import java.util.List;

/**
 * 주소위치정보 repository 인터페이스
 */
public interface AddressPositionRepository {

    /**
     * 파라미터 entity를 모두 저장한다.
     * @param entities 저장할 entity 목록
     */
    void saveAll(List<? extends AddressPosition> entities);
}
