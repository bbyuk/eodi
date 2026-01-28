package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import com.bb.eodi.legaldong.application.cache.LegalDongCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 실거래가 - 법정동 캐시 어댑터
 */
@Component
@RequiredArgsConstructor
public class DealLegalDongCacheAdapter implements LegalDongCachePort {

    private final LegalDongCache cache;

    @Override
    public LegalDongInfo findById(Long id) {
        return cache.findById(id);
    }

    @Override
    public LegalDongInfo findByCode(String code) {
        return cache.findByCode(code);
    }
}
