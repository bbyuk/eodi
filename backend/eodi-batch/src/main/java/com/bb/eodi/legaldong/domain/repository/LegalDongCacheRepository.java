package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.dto.LegalDongInfoDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;

import java.util.Optional;

/**
 * 법정동 캐시 레포지토리
 */
public interface LegalDongCacheRepository {

    /**
     * 법정동 ID로 법정동 캐시 조회를 수행한다.
     * @param id 법정동 ID
     * @return 법정동 정보 DTO
     */
    Optional<LegalDongInfoDto> findLegalDongInfoById(Long id);

    /**
     * 법정동코드로 법정동 캐시 조회를 수행한다.
     * @param code 법정동 코드
     * @return 법정동 정보 DTO
     */
    Optional<LegalDongInfoDto> findLegalDongInfoByCode(String code);
}
