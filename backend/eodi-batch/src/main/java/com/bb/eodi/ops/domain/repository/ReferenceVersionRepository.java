package com.bb.eodi.ops.domain.repository;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;

import java.time.LocalDate;
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

    /**
     * 기준정보 버전을 생성한다.
     * @param referenceVersion 기준정보 버전
     */
    void insert(ReferenceVersion referenceVersion);

    /**
     * 기준대상명으로 대상 기준정보버전의 effective_date 값을 업데이트한다.
     * @param value 업데이트 값
     * @param referenceVersionName 기준정보 대상명
     * @return 업데이트 수
     */
    void updateEffectiveDateByReferenceVersionName(LocalDate value, String referenceVersionName);
}
