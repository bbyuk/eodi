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

    /**
     * 대상 enetity 목록을 일변동분 처리 룰에 따라 batch update한다.
     * @param entities update 대상 entity chunk
     */
    void updateBatch(List<? extends LandLotAddress> entities);


    /**
     * 대상 entity 목록을 일변동분 처리 룰에 따라 batch delete한다.
     * @param entities delete 대상 entity chunk
     */
    void deleteBatch(List<LandLotAddress> entities);
}
