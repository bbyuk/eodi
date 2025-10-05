package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.dto.LegalDongSummaryDto;
import com.bb.eodi.domain.legaldong.entity.LegalDong;

import java.util.List;
import java.util.Optional;

/**
 * 법정동 repository
 */
public interface LegalDongRepository {

    /**
     * 법정동 코드로 LegalDong entity를 조회한다.
     * @param code
     * @return legalDong
     */
    Optional<LegalDong> findByCode(String code);

    /**
     * 법정동 명으로 시군구 최상위 코드 조회
     * @param name 명
     * @return 대상 명에 해당하는 시군구 최상위 코드
     */
    Optional<LegalDong> findTopSigunguCodeByName(String name);

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
