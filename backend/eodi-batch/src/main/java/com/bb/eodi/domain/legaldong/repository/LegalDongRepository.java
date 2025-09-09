package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;

import java.util.List;

/**
 * 법정동코드 repository
 */
public interface LegalDongRepository {

    /**
     * 법정동 배치 merge
     * @param data
     */
    void mergeBatch(List<? extends LegalDong> data);
}
