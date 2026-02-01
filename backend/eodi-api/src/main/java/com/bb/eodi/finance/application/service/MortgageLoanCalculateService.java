package com.bb.eodi.finance.application.service;

import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;
import com.bb.eodi.finance.application.result.MortgageLoanCalculateResult;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주택담보대출 계산 서비스
 */
@Service
@RequiredArgsConstructor
public class MortgageLoanCalculateService {

    private final RegulatingAreaRepository regulatingAreaRepository;
    private final MortgagePolicy mortgagePolicy;

    /**
     * 주택담보대출 한도 계산
     * @param input 주택담보대출 한도 계산 입력 파라미터
     * @return 주택담보대출 한도 계산 결과
     */
    @Transactional(readOnly = true)
    public MortgageLoanCalculateResult calculateMortgageLoanLimit(MortgageLoanLimitCalculateInput input) {
        boolean isRegulatingArea = regulatingAreaRepository.isRegulatingArea(
                input.getHouseInfo()
                        .getLegalDongId()
        );

        return MortgageLoanCalculateResult.builder()
                .hasLimitAmount(isRegulatingArea)
                .limitAmount(mortgagePolicy.calculateLimitAmount(input))
                .ltv(mortgagePolicy.calculateLtv(input))
                .build();

    }

    public int calculateMaximumMortgageLoanAmount(int cash) {
        return (cash * mortgagePolicy.getDefaultLtv()) / (100 - mortgagePolicy.getDefaultLtv());
    }
}
