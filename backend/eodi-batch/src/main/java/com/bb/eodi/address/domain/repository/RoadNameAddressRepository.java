package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.RoadNameAddress;

import java.util.List;

/**
 * 도로명주소 repository
 */
public interface RoadNameAddressRepository {

    /**
     * 배치 insert를 수행한다.
     * @param items 배치 insert할 데이터
     */
    void insertBatch(List<? extends RoadNameAddress> items);
}
