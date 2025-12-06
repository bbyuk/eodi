package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.RoadNameCode;

import java.util.List;

/**
 * 도로명코드 Repository
 */
public interface RoadNameCodeRepository {
    /**
     * 도로명코드 배치 insert
     * @param items insert 대상 데이터
     */
    void insertBatch(List<? extends RoadNameCode> items);
}
