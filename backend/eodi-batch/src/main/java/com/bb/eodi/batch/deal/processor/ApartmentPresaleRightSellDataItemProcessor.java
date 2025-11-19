package com.bb.eodi.batch.deal.processor;

import com.bb.eodi.batch.deal.entity.RealEstateSell;
import com.bb.eodi.batch.deal.type.HousingType;
import com.bb.eodi.batch.deal.type.TradeMethodType;
import com.bb.eodi.batch.legaldong.entity.LegalDong;
import com.bb.eodi.batch.legaldong.repository.LegalDongRepository;
import com.bb.eodi.port.out.deal.dto.ApartmentPresaleRightSellDataItem;
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
public class ApartmentPresaleRightSellDataItemProcessor
        implements ItemProcessor<ApartmentPresaleRightSellDataItem, RealEstateSell> {

    private final LegalDongRepository legalDongRepository;
    private static final String legalDongCodePostfix = "00000";

    // 해제사유발생일 date 입력 formatter
    private static final String cancelDateFormat = "yy.MM.dd";
    private static final String dateOfRegistrationFormat = "yy.MM.dd";

    @Override
    public RealEstateSell process(ApartmentPresaleRightSellDataItem item) throws Exception {
        log.info("ApartmentPresaleRightSellDataItemProcessor.process called");
        log.debug("item : {}", item);

        // 법정동코드 조회
        LegalDong legalDong = legalDongRepository.findByCode(item.sggCd().concat(legalDongCodePostfix))
                .orElseThrow(() -> new RuntimeException("매칭되는 법정동 코드가 없습니다."));

        return RealEstateSell.builder()
                .regionId(legalDong.getId())
                .legalDongName(item.umdNm())
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
                .netLeasableArea(new BigDecimal(item.excluUseAr()))
                .buyer(item.buyerGbn())
                .seller(item.slerGbn())
                .housingType("입".equals(item.ownershipGbn()) ? HousingType.OCCUPY_RIGHT : HousingType.PRESALE_RIGHT)
                .targetName(item.aptNm())
                .floor(
                        StringUtils.hasText(item.floor()) ?
                                Integer.parseInt(item.floor()) : null)
                .isLandLease(false)
                .build();
    }
}
