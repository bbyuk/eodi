package com.bb.eodi.finance.application.input;

import lombok.Builder;
import lombok.Data;

/**
 * 주택담보대출 한도 계산 Input
 */
@Data
@Builder
public class MortgageLoanLimitCalculateInput {

    private PersonInfo personInfo;

    private HouseInfo houseInfo;

    @Data
    @Builder
    public static class PersonInfo {
        // 연소득
        private Long annualIncome;

        // 월상환액
        private Long monthlyPayment;

        // 생애최초 여부
        private Boolean isFirstTimeBuyer = false;

        public Boolean isFirstTimeBuyer() {
            return isFirstTimeBuyer;
        }
    }

    @Data
    @Builder
    public static class HouseInfo {
        // 가격
        private long price;

        // 법정동 id
        private Long legalDongId;
    }

}
