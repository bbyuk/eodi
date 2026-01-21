package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.dto.LegalDongSummaryDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;

import java.util.List;
import java.util.Optional;

/**
 * 법정동 repository
 */
public interface LegalDongRepository {

    /**
     * 법정동 코드로 active LegalDong entity를 조회한다.
     * @param code 법정동 코드
     * @return 법정동
     */
    Optional<LegalDong> findByCode(String code);

    /**
     * 법정동 코드로 현재 상태에 상관없이 법정동 entity를 조회한다.
     * @param code 법정동 코드
     * @return 법정동
     */
    Optional<LegalDong> findAnyByCode(String code);

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

    /**
     * 법정동의 시도코드, 시군구코드, 동명으로 법정동을 조회한다.
     * @param sidoCode 시도코드
     * @param sigunguCode 시군구코드
     * @param legalDongName 동명
     */
    Optional<LegalDong> findBySidoCodeAndSigunguCodeAndLegalDongName(String sidoCode, String sigunguCode, String legalDongName);
}
