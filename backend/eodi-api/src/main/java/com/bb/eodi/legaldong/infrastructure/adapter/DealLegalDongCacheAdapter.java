package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.contract.DealLegalDongInfo;
import com.bb.eodi.deal.application.port.DealLegalDongCachePort;
import com.bb.eodi.legaldong.application.cache.LegalDongCache;
import com.bb.eodi.legaldong.infrastructure.adapter.mapper.LegalDongInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 실거래가 - 법정동 캐시 어댑터
 */
@Component
@RequiredArgsConstructor
public class DealLegalDongCacheAdapter implements DealLegalDongCachePort {

    private final LegalDongCache cache;

    private final LegalDongInfoMapper mapper;

    @Override
    public DealLegalDongInfo findById(Long id) {
        return mapper.forDeal(cache.findById(id));
    }

    @Override
    public DealLegalDongInfo findByCode(String code) {
        return mapper.forDeal(cache.findByCode(code));
    }
}
