package com.bb.eodi.ops.domain.repository;

import com.bb.eodi.ops.domain.entity.ReferenceVersion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 기준정보 버전 Repository
 */
public interface ReferenceVersionRepository {

    Optional<LocalDate> findLastUpdateDate(List<String> targetNames);
}
