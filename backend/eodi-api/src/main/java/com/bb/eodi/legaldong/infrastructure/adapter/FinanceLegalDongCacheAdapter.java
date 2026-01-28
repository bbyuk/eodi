package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.finance.application.port.FinanceLegalDongCachePort;
import com.bb.eodi.legaldong.application.cache.LegalDongCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 주택담보대출 규제지역 관련 법정동 캐시 adapter
 */
@Component
@RequiredArgsConstructor
public class FinanceLegalDongCacheAdapter implements FinanceLegalDongCachePort {

    private final LegalDongCache cache;

    /**
     * 법정동 명으로 법정동 정보를 캐시에서 조회한다.
     * @param name 법정동명
     * @return 법정동 정보
     */
    @Override
    public LegalDongInfo findByName(String name) {
        return cache.findByName(name);
    }
}
