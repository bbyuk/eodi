package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.dto.LandLot;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.type.TradeMethodType;
import com.bb.eodi.deal.job.dto.ApartmentPresaleRightSellDataItem;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
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
import java.time.format.DateTimeFormatter;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentPresaleRightSellDataItemProcessor
        implements ItemProcessor<ApartmentPresaleRightSellDataItem, RealEstateSell> {

    private final LegalDongRepository legalDongRepository;
    private static final String legalDongCodePostfix = "00000";

    @Value("#{jobExecutionContext['apartment-presale-lastUpdateDate']}")
    private LocalDate lastUpdateDate;

    // 해제사유발생일 date 입력 formatter
    private static final String cancelDateFormat = "yy.MM.dd";
    private static final String dateOfRegistrationFormat = "yy.MM.dd";

    @Override
    public RealEstateSell process(ApartmentPresaleRightSellDataItem item) throws Exception {
        /**
         * 마지막 업데이트 일자 기준으로 마지막 업데이트 일자 이전날짜까지의 dealDate는 이미 load된 것으로 간주하고 skip
         */
        LocalDate dealDate = LocalDate.of(
                Integer.parseInt(item.dealYear()),
                Integer.parseInt(item.dealMonth()),
                Integer.parseInt(item.dealDay()));
        if (!lastUpdateDate.isAfter(dealDate)) {
            return null;
        }


        log.info("ApartmentPresaleRightSellDataItemProcessor.process called");
        log.debug("item : {}", item);

        // 법정동코드 조회
        LegalDong legalDong = legalDongRepository.findByCode(item.sggCd().concat(legalDongCodePostfix))
                .orElseThrow(() -> new RuntimeException("매칭되는 법정동 코드가 없습니다."));
        LandLot landLot = new LandLot(item.jibun());


        return RealEstateSell.builder()
                .regionId(legalDong.getId())
                .legalDongName(item.umdNm())
                .landLotValue(landLot.getValue())
                .landLotMainNo(landLot.getLandLotMainNo())
                .landLotSubNo(landLot.getLandLotSubNo())
                .isMountain(landLot.getIsMountain())
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
