package com.bb.eodi.batch.job.deal.load.processor;

import com.bb.eodi.domain.deal.entity.RealEstateSell;
import com.bb.eodi.domain.deal.type.HousingType;
import com.bb.eodi.domain.deal.type.TradeMethodType;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentSellDataItemProcessor implements ItemProcessor<ApartmentSellDataItem, RealEstateSell> {

    private final LegalDongRepository legalDongRepository;
    private static final String legalDongCodePostfix = "00000";

    // 해제사유발생일 date 입력 formatter
    private static final String cancelDateFormat = "yyyyMMdd";
    private static final String dateOfRegistrationFormat = "yy.MM.dd";

    @Override
    public RealEstateSell process(ApartmentSellDataItem item) throws Exception {
        log.info("ApartmentSellDataItemProcessor.process called");
        log.debug("item : {}", item);

        // 법정동코드 조회
        LegalDong legalDong = legalDongRepository.findTopSigunguCodeByName(item.tempSggNm())
                .orElseThrow(() -> new RuntimeException("매칭되는 법정동 코드가 없습니다."));

        // item.tempSggNm() -> 서울특별시 강남구 대치동
        // legalDong.name() -> 서울틁별시 강남구



        return RealEstateSell.builder()
                .regionId(legalDong.getId())
                .legalDongName(legalDong.getName())
                .contractDate(
                        LocalDate.of(
                                Integer.parseInt(item.dealYear()),
                                Integer.parseInt(item.dealMonth()),
                                Integer.parseInt(item.dealDay())
                        )
                )
                .price(Long.parseLong(item.dealAmount().replace(",", "")))
                .tradeMethodType(TradeMethodType.fromData(item.dealingGbn()))
                // item.cdealDay -> "-"인 case 존재
                .cancelDate(
                        StringUtils.hasText(item.cdealDay().trim()) && !item.cdealDay().equals("-")
                                ? LocalDate.parse(
                                item.cdealDay(),
                                DateTimeFormatter.ofPattern(cancelDateFormat)
                        )
                                : null
                )
                .buildYear(StringUtils.hasText(item.buildYear()) ? Integer.parseInt(item.buildYear()) : null)
                .netLeasableArea(new BigDecimal(item.excluUseAr()))
                .buyer(item.buyerGbn())
                .seller(item.slerGbn())
                .housingType(HousingType.APT)
                .dateOfRegistration(
                        StringUtils.hasText(item.rgstDate()) && !item.rgstDate().equals("-")
                                ? LocalDate.parse(
                                item.rgstDate(),
                                DateTimeFormatter.ofPattern(dateOfRegistrationFormat)
                        )
                                : null
                )
                .targetName(item.aptNm())
                .buildingDong("-".equals(item.aptDong()) ? null : item.aptDong())
                .floor(Integer.parseInt(item.floor()))
                .isLandLease(item.landLeaseholdGbn().equals("Y"))
                .build();
    }
}
