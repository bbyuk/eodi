package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.LandLotAddress;

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
}
