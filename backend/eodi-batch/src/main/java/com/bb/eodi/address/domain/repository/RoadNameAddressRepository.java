package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.entity.RoadNameAddress;

import java.util.List;
import java.util.Optional;

/**
 * 도로명주소 repository
 */
public interface RoadNameAddressRepository {

    /**
     * 배치 insert를 수행한다.
     * @param items 배치 insert할 데이터
     */
    void insertBatch(List<? extends RoadNameAddress> items);

    /**
     * 주소 관리번호로 도로명주소를 검색한다.
     * @param addressManageNo 주소 관리 번호
     * @return 도로명 주소
     */
    Optional<RoadNameAddress> findByManageNo(String addressManageNo);
}
