package com.bb.eodi.finance.application.service;

import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;
import com.bb.eodi.finance.application.result.MortgageLoanLimitCalculateResult;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주택담보대출 계산 서비스
 */
@Service
@RequiredArgsConstructor
public class MortgageLoanCalculatingService {

    private final RegulatingAreaRepository regulatingAreaRepository;


    /**
     * 주택담보대출 한도 계산
     * @param input 주택담보대출 한도 계산 입력 파라미터
     * @return 주택담보대출 한도 계산 결과
     */
    @Transactional
    public MortgageLoanLimitCalculateResult calculateMortgageLoanLimit(MortgageLoanLimitCalculateInput input) {

        // 규제지역여부
        // 주택보유여부
        //

        return MortgageLoanLimitCalculateResult.builder()
                .amount(0)
                .build();
    }

}
