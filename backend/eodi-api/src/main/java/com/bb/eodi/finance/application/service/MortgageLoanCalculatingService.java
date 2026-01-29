//package com.bb.eodi.finance.application.service;
//
//import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;
//import com.bb.eodi.finance.application.result.MortgageLoanCalculateResult;
//import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * 주택담보대출 계산 서비스
// */
//@Service
//@RequiredArgsConstructor
//public class MortgageLoanCalculatingService {
//
//    private final RegulatingAreaRepository regulatingAreaRepository;
//    private final MortgagePolicy mortgagePolicy;
//
//    /**
//     * 주택담보대출 한도 계산
//     * @param input 주택담보대출 한도 계산 입력 파라미터
//     * @return 주택담보대출 한도 계산 결과
//     */
//    @Transactional
//    public MortgageLoanCalculateResult calculateMortgageLoanLimit(MortgageLoanLimitCalculateInput input) {
//        boolean isRegulatingArea = regulatingAreaRepository.isRegulatingArea(
//                input.getHouseInfo()
//                        .getLegalDongId()
//        );
//
//        return MortgageLoanCalculateResult.builder()
//                .hasLimitAmount(isRegulatingArea)
//                .limitAmount(mortgagePolicy.calculateLimitAmount(input))
//                .ltv(mortgagePolicy.calculateLtv(input))
//                .build();
//
//    }
//
//    /**
//     * 한도 금액을 계산한다.
//     * @param price
//     * @return
//     */
//    private int calculateLimitAmount(int price) {
//        if (price <= 150_000) {
//            return 60_000;
//        }
//        else if (price <= 250_000) {
//            return 40_000;
//        }
//        else {
//            return 20_000;
//        }
//    }
//}
