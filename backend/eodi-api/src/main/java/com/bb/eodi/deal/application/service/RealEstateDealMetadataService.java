package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.port.DealReferenceVersionPort;
import com.bb.eodi.deal.application.result.RealEstateDealMetadataRetrieveResult;
import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 부동산 실거래가 메타데이터 서비스
 */
@Service
@RequiredArgsConstructor
public class RealEstateDealMetadataService {

    private final DealReferenceVersionPort dealReferenceVersionPort;

    /**
     * 부동산 실거래가 데이터의 메타데이터를 조회한다.
     * @return 부동산 실거래가 데이터 메타데이터
     *  - 업데이트 일자
     */
    @Transactional(readOnly = true)
    public RealEstateDealMetadataRetrieveResult findRealEstateDealMetadata() {

        // 업데이트 일자는 모든 데이터 기준 가장 오래된 시점의 시간
        // HousingType.name()-DealType.name()

        HousingType[] housingTypes = HousingType.values();
        DealType[] dealTypes = DealType.values();
        List<String> targetNames = new ArrayList<>();

        for (HousingType housingType : housingTypes) {
            if (!housingType.isEnabled()) continue;
            for (DealType dealType : dealTypes) {
                targetNames.add(housingType.name() + "-" + dealType.name());
            }
        }

        return new RealEstateDealMetadataRetrieveResult(
                dealReferenceVersionPort.findLastUpdatedDate(targetNames)
        );
    }
}
