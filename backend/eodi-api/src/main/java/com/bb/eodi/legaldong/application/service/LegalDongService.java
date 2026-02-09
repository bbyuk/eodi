package com.bb.eodi.legaldong.application.service;

import com.bb.eodi.legaldong.application.input.RegionFindInput;
import com.bb.eodi.legaldong.application.result.LegalDongFindResult;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.query.LegalDongFindQuery;
import com.bb.eodi.legaldong.domain.query.LegalDongScope;
import com.bb.eodi.legaldong.domain.read.LegalDongQueryRepository;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 법정동 서비스 컴포넌트
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LegalDongService {

    private final LegalDongQueryRepository legalDongQueryRepository;
    private final LegalDongRepository legalDongRepository;

    /**
     * 최상위 법정동 (시도) 목록을 조회한다.
     * @return 시도 목록
     */
    @Transactional(readOnly = true)
    public List<LegalDongFindResult> findAllRootLegalDongs() {
        return legalDongQueryRepository.findBy(new LegalDongFindQuery(LegalDongScope.SIDO, null))
                .stream()
                .map(legalDong -> LegalDongFindResult.builder()
                        .id(legalDong.getId())
                        .code(legalDong.getCode())
                        .name(legalDong.getName())
                        .displayName(legalDong.getName())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 지역 단위 법정동 (시군구) 목록을 조회한다.
     * @return 시군구 목록
     */
    @Transactional(readOnly = true)
    public List<LegalDongFindResult> findAllRegionLegalDongs(RegionFindInput input) {
        LegalDong rootLegalDong = legalDongRepository.findByCode(input.code())
                .orElseThrow(() -> new RuntimeException("최상위 법정동을 찾지 못했습니다."));

        return legalDongQueryRepository
                .findBy(new LegalDongFindQuery(LegalDongScope.SIGUNGU, input.code()))
                .stream()
                .map(legalDong -> LegalDongFindResult.builder()
                        .id(legalDong.getId())
                        .code(legalDong.getCode())
                        .name(legalDong.getName())
                        .displayName(legalDong.getName().replace(rootLegalDong.getName(), "").trim())
                        .build())
                .collect(Collectors.toList());
    }

}
