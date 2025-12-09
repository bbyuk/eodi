package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.entity.RoadNameAddress;

import java.util.Collection;
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

    /**
     * 주소 관리번호 목록에 해당하는 도로명주소 목록을 조회한다.
     * @param addressManageNoList 조회 대상 주소 관리번호 목록
     * @return 도로명주소 목록
     */
    List<RoadNameAddress> findAllByManageNoList(List<String> addressManageNoList);
    
    /**
     * 도로명주소 부가정보에 해당하는 컬럼을 batch update 한다.
     * @param items batch update 대상 item 목록
     */
    void batchUpdateAdditionalInfo(Collection<? extends RoadNameAddress> items);


    /**
     * 지번주소와 join하여 도로명주소 조회
     * @param parameter 쿼리 파라미터
     * @return 도로명주소 목록
     */
    List<RoadNameAddress> findWithLandLot(RoadNameAddressQueryParameter parameter);
}
