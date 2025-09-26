package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.entity.LegalDong;

import java.util.Optional;

/**
 * 법정동 도메인 repository
 */
public interface LegalDongRepository {
    /**
     * 법정동코드로 법정동 entity 조회
     * @param code 조회대상 법정동 코드
     * @return 법정동 코드
     */
    Optional<LegalDong> findByCode(String code);
}
