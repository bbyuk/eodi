package com.bb.eodi.batch.legaldong.repository;

import com.bb.eodi.batch.legaldong.dto.LegalDongSummaryDto;
import com.bb.eodi.batch.legaldong.entity.LegalDong;

import java.util.List;
import java.util.Optional;

/**
 * 법정동 repository
 */
public interface LegalDongRepository {

    /**
     * ID로 LegalDong entity를 조회한다.
     * @param id 법정동 ID
     * @return 법정동
     */
    Optional<LegalDong> findById(Long id);

    /**
     * 법정동 코드로 LegalDong entity를 조회한다.
     * @param code 법정동 코드
     * @return 법정동
     */
    Optional<LegalDong> findByCode(String code);

    /**
     * 법정동 적재 배치 merge
     * @param data
     */
    void mergeBatch(List<? extends LegalDong> data);

    /**
     * 법정동 상위 ID 설정
     * @param data
     */
    void mappingParentIdBatch(List<? extends LegalDong> data);

    /**
     * 법정동 요약 정보 View 조회
     * Cross domain context 제공
     * @return 법정동 요약 정보 view list
     */
    List<LegalDongSummaryDto> findAllSummary();

}
