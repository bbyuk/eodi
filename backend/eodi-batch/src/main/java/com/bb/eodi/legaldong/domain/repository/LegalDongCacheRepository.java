package com.bb.eodi.legaldong.domain.repository;

import com.bb.eodi.legaldong.domain.entity.LegalDong;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 법정동 캐시 레포지토리
 */
@Component
@RequiredArgsConstructor
public class LegalDongCacheRepository {
    private final LegalDongJpaRepository legalDongRepository;
    private static final Map<Long, LegalDong> IN_MEMORY_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
        IN_MEMORY_CACHE.putAll(legalDongRepository.findAll()
                .stream()
                .collect(
                        Collectors.toMap(LegalDong::getId, entity -> entity)
                )
        );
    }

    /**
     * 지역 id + 동명으로 대상 법정동을 조회한다.
     * @param regionId
     * @param dongName
     * @return
     */
    public Optional<LegalDong> findTargetByRegionIdAndDongName(Long regionId, String dongName) {
        LegalDong region = IN_MEMORY_CACHE.get(regionId);
        return IN_MEMORY_CACHE.values().stream().filter(legalDong -> 
                legalDong.getSidoCode().equals(region.getSidoCode()) 
                && legalDong.getSigunguCode().equals(region.getSigunguCode())
                && legalDong.getName().equals(region.getName() + " " + dongName))
                .findFirst();
    }
}
