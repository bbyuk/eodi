package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.Sigungu;

import java.util.Optional;

/**
 * 법정동코드 시/군/구 repository
 */
public interface LegalDongSigunguRepository {
    Optional<Sigungu> findByCode(String code);
}
