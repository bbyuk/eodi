package com.bb.eodi.finance.application.service;

import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 2025. 10. 15 부동산대책 대출 규제 정책 구현체
 */
@Component
@RequiredArgsConstructor
public class MortgagePolicy1015 implements MortgagePolicy {

    private final RegulatingAreaRepository regulatingAreaRepository;
    private static final int DEFAULT_LTV = 70;

    @Override
    public int getDefaultLtv() {
        return DEFAULT_LTV;
    }

    @Override
    @Transactional(readOnly = true)
    public int calculateLtv(MortgageLoanLimitCalculateInput input) {
        boolean isRegulatingArea = regulatingAreaRepository.isRegulatingArea(input.getHouseInfo().getLegalDongId());
        boolean isFirstTimeBuyer = input.getPersonInfo().isFirstTimeBuyer();


        return isRegulatingArea && !isFirstTimeBuyer ? 40 : 70;
    }

    /**
     * 규제지역일 경우 대출금 최대 금액 제한
     * @param input 주택담보대출 한도 계산 입력
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public long calculateLimitAmount(MortgageLoanLimitCalculateInput input) {
        boolean isRegulatingArea = regulatingAreaRepository.isRegulatingArea(input.getHouseInfo().getLegalDongId());

        if (isRegulatingArea) {
            long price = input.getHouseInfo().getPrice();

            if (price <= 150_000) {
                return 60_000;
            }
            else if (price <= 250_000) {
                return 40_000;
            }
            else {
                return 20_000;
            }
        }

        return -1;
    }
}
