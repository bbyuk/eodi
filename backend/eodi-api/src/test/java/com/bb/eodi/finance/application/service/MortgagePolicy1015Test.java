package com.bb.eodi.finance.application.service;

import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;
import com.bb.eodi.finance.domain.repository.RegulatingAreaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MortgagePolicy1015.class })
@DisplayName("small - 1015 부동산대책 대응 주택담보대출 정책 단위 테스트")
class MortgagePolicy1015Test {

    @MockitoBean
    RegulatingAreaRepository regulatingAreaRepository;

    @Autowired
    MortgagePolicy mortgagePolicy;

    @Test
    @DisplayName("규제지역 - 생애최초 -> LTV 70")
    void testIsRegulatingAreaAndIsFirstTimeBuyer() throws Exception {
        // given
        given(regulatingAreaRepository.isRegulatingArea(anyLong()))
                .willReturn(true);

        MortgageLoanLimitCalculateInput input = MortgageLoanLimitCalculateInput.builder()
                .personInfo(
                        MortgageLoanLimitCalculateInput.PersonInfo
                                .builder()
                                .isFirstTimeBuyer(true)
                                .build()
                )
                .houseInfo(
                        MortgageLoanLimitCalculateInput.HouseInfo
                                .builder()
                                .legalDongId(1L)
                                .build()
                )
                .build();

        // when
        int ltv = mortgagePolicy.calculateLtv(input);

        // then
        Assertions.assertThat(ltv).isEqualTo(70);
    }

    @Test
    @DisplayName("규제지역 - 생애최초 아님 -> LTV 40")
    void testIsRegulatingAreaAndIsNotFirstTimeBuyer() throws Exception {
        // given
        given(regulatingAreaRepository.isRegulatingArea(anyLong()))
                .willReturn(true);

        MortgageLoanLimitCalculateInput input = MortgageLoanLimitCalculateInput.builder()
                .personInfo(
                        MortgageLoanLimitCalculateInput.PersonInfo
                                .builder()
                                .isFirstTimeBuyer(false)
                                .build()
                )
                .houseInfo(
                        MortgageLoanLimitCalculateInput.HouseInfo
                                .builder()
                                .legalDongId(1L)
                                .build()
                )
                .build();

        // when
        int ltv = mortgagePolicy.calculateLtv(input);

        // then
        Assertions.assertThat(ltv).isEqualTo(40);
    }

    @Test
    @DisplayName("규제지역 아님 -> LTV 70")
    void testIsNotRegulatingArea() throws Exception {
        // given
        given(regulatingAreaRepository.isRegulatingArea(anyLong()))
                .willReturn(false);


        MortgageLoanLimitCalculateInput input = MortgageLoanLimitCalculateInput.builder()
                .personInfo(
                        MortgageLoanLimitCalculateInput.PersonInfo
                                .builder()
                                .isFirstTimeBuyer(false)
                                .build()
                )
                .houseInfo(
                        MortgageLoanLimitCalculateInput.HouseInfo
                                .builder()
                                .legalDongId(1L)
                                .build()
                )
                .build();
        // when
        int ltv = mortgagePolicy.calculateLtv(input);

        // then
        Assertions.assertThat(ltv).isEqualTo(70);
    }
}