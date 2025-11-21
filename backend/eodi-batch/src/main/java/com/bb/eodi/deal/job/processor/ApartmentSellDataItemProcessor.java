package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.type.TradeMethodType;
import com.bb.eodi.deal.job.util.DealDataParser;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.bb.eodi.deal.job.dto.ApartmentSellDataItem;
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
    private static final String cancelDateFormat = "yy.MM.dd";
    private static final String dateOfRegistrationFormat = "yy.MM.dd";

    @Override
    public RealEstateSell process(ApartmentSellDataItem item) throws Exception {
        log.info("ApartmentSellDataItemProcessor.process called");
        log.debug("item : {}", item);

        // 법정동코드 조회
        LegalDong legalDong = legalDongRepository.findByCode(item.sggCd().concat(legalDongCodePostfix))
                .orElseThrow(() -> new RuntimeException("매칭되는 법정동 코드가 없습니다."));
        Integer[] jibun = DealDataParser.parseJibun(item.jibun());

        return RealEstateSell.builder()
                .regionId(legalDong.getId())
                .legalDongName(item.umdNm())
                .landLotMainNo(jibun[0])
                .landLotSubNo(jibun[1])
                .contractDate(
                        LocalDate.of(
                                Integer.parseInt(item.dealYear()),
                                Integer.parseInt(item.dealMonth()),
                                Integer.parseInt(item.dealDay())
                        )
                )
                .price(Long.parseLong(item.dealAmount().replace(",", "")))
                .tradeMethodType(TradeMethodType.fromData(item.dealingGbn()))
                .cancelDate(
                        StringUtils.hasText(item.cdealDay().trim())
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
                        StringUtils.hasText(item.rgstDate())
                                ? LocalDate.parse(
                                item.rgstDate(),
                                DateTimeFormatter.ofPattern(dateOfRegistrationFormat)
                        )
                                : null
                )
                .targetName(item.aptNm())
                .buildingDong(item.aptDong())
                .floor(Integer.parseInt(item.floor()))
                .isLandLease(item.landLeaseholdGbn().equals("Y"))
                .build();
    }
}
