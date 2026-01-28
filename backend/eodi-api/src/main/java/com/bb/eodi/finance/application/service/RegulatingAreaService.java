package com.bb.eodi.finance.application.service;

import com.bb.eodi.deal.application.contract.LegalDongInfo;
import com.bb.eodi.finance.application.input.RegulatingAreaRegisterInput;
import com.bb.eodi.finance.application.port.FinanceLegalDongCachePort;
import com.bb.eodi.finance.application.result.RegulatingAreaRegisterResult;
import com.bb.eodi.finance.domain.entity.RegulatingArea;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 규제지역 application service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegulatingAreaService {

    private final RegulatingAreaRepository regulatingAreaRepository;
    private final FinanceLegalDongCachePort legalDongCachePort;

    /**
     * 입력받은 application input에 해당하는 법정동을 조회해 규제지역으로 지정한다.
     *
     * @param input
     */
    @Transactional
    public RegulatingAreaRegisterResult register(RegulatingAreaRegisterInput input) {

        List<LegalDongInfo> regions = input.targetRegionNames().stream()
                .map(legalDongCachePort::findByName)
                .toList();

        List<RegulatingArea> targets = new ArrayList<>();

        for (LegalDongInfo legalDongInfo : regions) {
            traverse(input.effectiveStartDate(), input.effectiveEndDate(), legalDongInfo, targets);
        }

        regulatingAreaRepository.saveAll(targets);
        int size = regulatingAreaRepository.findAll().size();

        return RegulatingAreaRegisterResult
                .builder()
                .count(size)
                .build();
    }

    /**
     * 법정동 정보 트리를 순회하며 규제지역 등록 트리를 만든다.
     *
     * @param effectiveStartDate 적용시작일자
     * @param effectiveEndDate   적용종료일자
     * @param legalDongInfo      법정동정보 노드
     * @param targets            규제지역 등록 트리
     */
    private void traverse(LocalDate effectiveStartDate, LocalDate effectiveEndDate, LegalDongInfo legalDongInfo, List<RegulatingArea> targets) {
        targets.add(
                RegulatingArea
                        .builder()
                        .legalDongId(legalDongInfo.id())
                        .effectiveStartDate(effectiveStartDate)
                        .effectiveEndDate(effectiveEndDate)
                        .build()
        );
        if (legalDongInfo.children().isEmpty()) {
            return;
        }

        for (LegalDongInfo child : legalDongInfo.children()) {
            traverse(effectiveStartDate, effectiveEndDate, child, targets);
        }
    }
}
