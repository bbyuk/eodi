package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.dto.LegalDongInfoDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;

import java.util.Optional;

/**
 * 법정동 캐시 레포지토리
 */
public interface LegalDongCacheRepository {

    Optional<LegalDongInfoDto> findLegalDongInfoById(Long id);
}
