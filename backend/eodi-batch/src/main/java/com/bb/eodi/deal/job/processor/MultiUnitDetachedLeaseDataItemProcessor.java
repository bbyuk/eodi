package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.bb.eodi.deal.job.dto.MultiUnitDetachedLeaseDataItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 단독/다가구주택 전월세 실거래가 데이터 적재 배치 chunk step ItemProcessor
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class MultiUnitDetachedLeaseDataItemProcessor implements ItemProcessor<MultiUnitDetachedLeaseDataItem, RealEstateLease> {

    private final LegalDongRepository legalDongRepository;

    @Value("#{jobExecutionContext['multi-unit-lease-lastUpdateDate']}")
    private LocalDate lastUpdateDate;
    private static final String legalDongCodePostfix = "00000";
    private static final String contractTermDelimiter = "~";
    private static final String contractTermYearMonthDelimiter = ".";
    private static final String numberDelimiter = ",";
    private static final int yearFixValue = 200000;

    @Override
    public RealEstateLease process(MultiUnitDetachedLeaseDataItem item) throws Exception {
        /**
         * 마지막 업데이트 일자 기준으로 마지막 업데이트 일자 이전날짜까지의 dealDate는 이미 load된 것으로 간주하고 skip
         */
        LocalDate dealDate = LocalDate.of(
                Integer.parseInt(item.dealYear()),
                Integer.parseInt(item.dealMonth()),
                Integer.parseInt(item.dealDay()));
        if (!lastUpdateDate.isBefore(dealDate)) {
            return null;
        }

        LegalDong legalDong = legalDongRepository.findByCode(item.sggCd().concat(legalDongCodePostfix))
                .orElseThrow(() -> new RuntimeException("매칭되는 법정동 코드가 없습니다."));

        String contractTerm = item.contractTerm().trim();


        return RealEstateLease.builder()
                .regionId(legalDong.getId())
                .legalDongName(item.umdNm())
                .contractDate(
                        LocalDate.of(
                                Integer.parseInt(item.dealYear()),
                                Integer.parseInt(item.dealMonth()),
                                Integer.parseInt(item.dealDay())
                        )
                )
                .contractStartMonth(
                        StringUtils.hasText(contractTerm) && StringUtils.hasText(contractTerm.split(contractTermDelimiter)[0].replace(contractTermYearMonthDelimiter, ""))
                                ? yearFixValue + Integer.parseInt(contractTerm.split(contractTermDelimiter)[0].replace(contractTermYearMonthDelimiter, ""))
                                : null
                )
                .contractEndMonth(
                        StringUtils.hasText(contractTerm) && StringUtils.hasText(contractTerm.split(contractTermDelimiter)[1].replace(contractTermYearMonthDelimiter, ""))
                                ? yearFixValue + Integer.parseInt(contractTerm.split(contractTermDelimiter)[1].replace(contractTermYearMonthDelimiter, ""))
                                :null
                )
                .deposit(Integer.parseInt(item.deposit().replace(numberDelimiter, "")))
                .monthlyRent(Integer.parseInt(item.monthlyRent().replace(numberDelimiter, "")))
                .previousDeposit(StringUtils.hasText(item.preDeposit().trim())
                        ? Integer.parseInt(item.preDeposit().replace(numberDelimiter, ""))
                        : null)
                .previousMonthlyRent(StringUtils.hasText(item.preMonthlyRent().trim())
                        ? Integer.parseInt(item.preMonthlyRent().replace(numberDelimiter, ""))
                        : null)
                .totalFloorArea(new BigDecimal(item.totalFloorAr()))
                .buildYear(StringUtils.hasText(item.buildYear().trim())
                        ? Integer.parseInt(item.buildYear())
                        : null)
                .housingType("단독".equals(item.houseType())
                        ? HousingType.DETACHED_HOUSE
                        : "다가구".equals(item.houseType())
                        ? HousingType.MULTI_UNIT_HOUSE
                        : HousingType.OTHER)
                .useRRRight("사용".equals(item.useRRRight()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
