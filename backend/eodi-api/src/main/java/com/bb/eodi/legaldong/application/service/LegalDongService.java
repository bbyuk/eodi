package com.bb.eodi.legaldong.application.service;

import com.bb.eodi.legaldong.application.input.RegionLegalDongFindInput;
import com.bb.eodi.legaldong.application.result.LegalDongFindResult;
import com.bb.eodi.legaldong.domain.query.LegalDongFindQuery;
import com.bb.eodi.legaldong.domain.query.LegalDongScope;
import com.bb.eodi.legaldong.domain.repository.LegalDongQueryRepository;
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
                        .id(legalDong.id())
                        .code(legalDong.code())
                        .name(legalDong.name())
                        .displayName(legalDong.name())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 지역 단위 법정동 (시군구) 목록을 조회한다.
     * @return 시군구 목록
     */
    @Transactional(readOnly = true)
    public List<LegalDongFindResult> findAllRegionLegalDongs(RegionLegalDongFindInput input) {
        return legalDongQueryRepository
                .findBy(new LegalDongFindQuery(LegalDongScope.SIGUNGU, input.code()))
                .stream()
                .map(legalDong -> LegalDongFindResult.builder()
                        .id(legalDong.id())
                        .code(legalDong.code())
                        .name(legalDong.name())
                        .displayName(legalDong.name().replace(legalDong.parentName(), "").trim())
                        .build())
                .collect(Collectors.toList());
    }

}
