package com.bb.eodi.finance.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 전세자금대출 계산 서비스
 */
@Service
@RequiredArgsConstructor
public class LeaseLoanCalculatingService {
    private final LeaseLoanPolicy leaseLoanPolicy;


    /**
     * 대상 물건의 임차보증금으로 전세자금대출 가능 최대 금액을 계산한다.
     * @param deposit 대상 물건 임차보증금
     * @return 전세자금대출 가능 최대 금액
     */
    @Transactional(readOnly = true)
    public long calculateMaximumLeaseLoanAmount(long deposit) {
        return deposit * leaseLoanPolicy.getLimitPercent() / 100;
    }
}
