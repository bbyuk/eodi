package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.Dong;

import java.util.Optional;

/**
 * 법정동코드 동 repository
 */
public interface LegalDongDongRepository {
    Optional<Dong> findByCode(String code);
}
