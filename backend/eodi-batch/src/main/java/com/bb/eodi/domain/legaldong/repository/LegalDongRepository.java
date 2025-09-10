package com.bb.eodi.domain.legaldong.repository;

import com.bb.eodi.domain.legaldong.entity.LegalDong;

import java.util.List;

/**
 * 법정동 repository
 */
public interface LegalDongRepository {

    /**
     * 법정동 적재 배치 merge
     * @param data
     */
    void mergeBatch(List<? extends LegalDong> data);

    /**
     * 법정동 상위 ID 설정
     * @param data
     */
    void mappingParentIdBatch(List<? extends LegalDong> data);
}
