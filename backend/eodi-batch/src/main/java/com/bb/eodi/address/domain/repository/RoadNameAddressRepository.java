package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.AddressPosition;
import com.bb.eodi.address.domain.dto.AddressPositionMappingParameter;
import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.entity.RoadNameAddress;

import java.util.Collection;
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

    /**
     * 도로명주소 주소위치 update 처리
     * @param items 주소위치 매핑 파라미터
     */
    void updatePosition(Collection<? extends RoadNameAddress> items);

    /**
     * 도로명주소 위치 정보 조회
     * @param parameter 쿼리 파라미터
     * @return 도로명주소 위치 정보 목록
     */
    List<AddressPosition> findAddressPositions(RoadNameAddressQueryParameter parameter);
}
