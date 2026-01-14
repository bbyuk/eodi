package com.bb.eodi.ops.domain.repository;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;

import java.util.Optional;

/**
 * 기준정보 버전 Repository
 */
public interface ReferenceVersionRepository {

    /**
     * 기준대상명으로 기준정보버전 조회
     * @param targetName 기준대상명
     * @return 기준정보버전 Optional
     */
    Optional<ReferenceVersion> findByTargetName(String targetName);
}
