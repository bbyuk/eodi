package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.job.util.DealDataParser;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.bb.eodi.deal.job.dto.ApartmentLeaseDataItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 아파트 임대차 실거래가 데이터 적재 배치 chunk step ItemProcessor
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentLeaseDataItemProcessor implements ItemProcessor<ApartmentLeaseDataItem, RealEstateLease> {

    private final LegalDongRepository legalDongRepository;
    private static final String legalDongCodePostfix = "00000";

    // 계약기간 필드 delimiter
    private static final String contractTermDelimiter = "~";
    private static final String contractTermYearMonthDelimiter = ".";
    private static final String numberDelimiter = ",";
    private static final int yearFixValue = 200000;

    @Override
    public RealEstateLease process(ApartmentLeaseDataItem item) throws Exception {

        LegalDong legalDong = legalDongRepository.findByCode(item.sggCd().concat(legalDongCodePostfix))
                .orElseThrow(() -> new RuntimeException("매칭되는 법정동 코드가 없습니다."));

        String contractTerm = item.contractTerm().trim();
        Integer[] jibun = DealDataParser.parseJibun(item.jibun());

        return RealEstateLease.builder()
                .regionId(legalDong.getId())
                .legalDongName(item.umdNm())
                .landLotMainNo(jibun[0])
                .landLotSubNo(jibun[1])
                .isMountain(jibun[2] != null && jibun[2] == 1)
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
                .buildYear(StringUtils.hasText(item.buildYear().trim())
                        ? Integer.parseInt(item.buildYear())
                        : null)
                .netLeasableArea(StringUtils.hasText(item.excluUseAr())
                        ? new BigDecimal(item.excluUseAr())
                        : null)
                .housingType(HousingType.APT)
                .floor(StringUtils.hasText(item.floor())
                        ? Integer.parseInt(item.floor())
                        :null)
                .useRRRight("사용".equals(item.useRRRight()))
                .targetName(item.aptNm())
                .build();
    }
}
