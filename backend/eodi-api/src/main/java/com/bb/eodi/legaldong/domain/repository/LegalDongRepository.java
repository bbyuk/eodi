
package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.entity.LegalDong;

import java.util.List;
import java.util.Optional;

/**
 * 법정동 도메인 repository
 */
public interface LegalDongRepository {
    /**
     * 법정동코드로 법정동 entity 조회
     * @param code 조회대상 법정동 코드
     * @return 법정동 entity
     */
    Optional<LegalDong> findByCode(String code);

    /**
     * 유효한 모든 법정동을 조회한다.
     * @return 법정동
     */
    List<LegalDong> findAll();


}
