package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.LandLotAddressFindQuery;
import com.bb.eodi.address.domain.entity.LandLotAddress;

import java.util.List;
import java.util.Optional;

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
     * 관리번호로 지번주소를 조회한다.
     * @param manageNo 관리번호
     * @return 관리번호에 해당하는 지번주소
     */
    Optional<LandLotAddress> findByManageNo(String manageNo);

    /**
     * 조회 쿼리 파라미터에 해당하는 대표 지번주소를 찾아 리턴한다.
     * @param query 조회 쿼리 파라미터
     * @return 지번주소 목록
     */
    List<LandLotAddress> findRepresentativeLandLotAddress(LandLotAddressFindQuery query);

    /**
     * 조회 쿼리 파라미터에 해당하는 대표 지번주소의 관리번호를 찾아 리턴한다.
     * @param query 조회 쿼리 파라미터
     * @return 대표 지번주소 관리번호
     */
    Optional<String> findRepresentativeLandLotAddressManageNo(LandLotAddressFindQuery query);

    /**
     * 부가정보에 해당하는 컬럼을 batch update한다.
     * @param items update 대상 chunk item
     */
    void batchUpdateAdditionalInfo(List<? extends LandLotAddress> items);
}
