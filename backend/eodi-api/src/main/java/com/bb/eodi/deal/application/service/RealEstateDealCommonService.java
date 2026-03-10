package com.bb.eodi.deal.application.service;

import com.bb.eodi.deal.application.result.HousingTypeCodeRetrieveResult;
import com.bb.eodi.deal.domain.type.HousingType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 부동산 실거래가 도메인 공통 데이터 Service
 */
@Service
public class RealEstateDealCommonService {

    /**
     * 현재 실거래가 데이터를 제공하는 모든 주택 유형 코드를 리턴한다.
     *
     * @return 현재 실거래가 데이터를 제공하는 모든 주택 유형 코드
     */
    public List<HousingTypeCodeRetrieveResult> findAllHousingTypeCodes() {
        return Arrays.stream(HousingType.values())
                .filter(HousingType::isEnabled)
                .map(
                type -> new HousingTypeCodeRetrieveResult(
                        type.code(),
                        type.description()
                )
        ).collect(Collectors.toList());
    }
}
