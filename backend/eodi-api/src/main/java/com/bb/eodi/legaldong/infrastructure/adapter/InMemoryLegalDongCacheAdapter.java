package com.bb.eodi.legaldong.infrastructure.adapter;

import com.bb.eodi.deal.application.model.LegalDongInfo;
import com.bb.eodi.deal.application.port.LegalDongCachePort;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 인메모리 법정동 캐시 어댑터
 */
@Repository
public class InMemoryLegalDongCacheAdapter implements LegalDongCachePort {

    private final Map<Long, Map<Long, Set<LegalDongInfo>>> hierarchy = new ConcurrentHashMap<>();
}
