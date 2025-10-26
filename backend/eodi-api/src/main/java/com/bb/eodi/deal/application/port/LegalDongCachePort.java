package com.bb.eodi.deal.application.port;

import com.bb.eodi.deal.application.model.LegalDongInfo;

/**
 * 법정동 캐시 조회 포트
 */
public interface LegalDongCachePort {
    LegalDongInfo findById(Long id);
}
