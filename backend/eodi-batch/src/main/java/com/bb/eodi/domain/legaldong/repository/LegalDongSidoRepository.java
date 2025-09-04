package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.Sido;

import java.util.Optional;

/**
 * 법정동코드 시/도 repository
 */
public interface LegalDongSidoRepository {
    Optional<Sido> findByCode(String code);
}
