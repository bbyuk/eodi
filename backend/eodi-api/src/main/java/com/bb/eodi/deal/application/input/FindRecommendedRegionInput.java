package com.bb.eodi.deal.application.input;

import java.util.List;

/**
 * 추천 지역 조회 application input
 */
public record FindRecommendedRegionInput(

        // 보유 예산
        Long cash,

        // 대출 여부
        Boolean hasLoan,

        // 생애최초 구매 여부
        Boolean isFirstTimeBuyer,

        // 대출 월 상환액
        Long monthlyPayment,

        // 연 소득
        Long annualIncome,

        // 대상 주택 유형 목록
        List<String> housingTypes
) {
}
