package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.domain.type.TradeMethodType;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import com.bb.eodi.deal.job.dto.MultiUnitDetachedSellDataItem;
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

/**
 * 단독/다가구주택 매매 데이터 적재 Chunk Step ItemProcessor
 */
@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class MultiUnitDetachedSellDataItemProcessor
        implements ItemProcessor<MultiUnitDetachedSellDataItem, RealEstateSell> {

    private final LegalDongRepository legalDongRepository;

    @Value("#{jobExecutionContext['multi-unit-sell-lastUpdateDate']}")
    private LocalDate lastUpdateDate;
    private static final String legalDongCodePostfix = "00000";

    // 해제사유발생일 date 입력 formatter
    private static final String cancelDateFormat = "yy.MM.dd";


    @Override
    public RealEstateSell process(MultiUnitDetachedSellDataItem item) throws Exception {
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

        log.info("multi unit-detached sell item process: {}", item);

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
                .buildYear(StringUtils.hasText(item.buildYear()) ? Integer.parseInt(item.buildYear()) : null)
                .landArea(StringUtils.hasText(item.plottageAr()) ? new BigDecimal(item.plottageAr()) : null)
                .totalFloorArea(StringUtils.hasText(item.totalFloorAr()) ? new BigDecimal(item.totalFloorAr()) : null)
                .buyer(item.buyerGbn())
                .seller(item.slerGbn())
                .housingType(
                        "단독".equals(item.houseType())
                                ? HousingType.DETACHED_HOUSE
                                : "다가구".equals(item.houseType())
                                ? HousingType.MULTI_UNIT_HOUSE
                                : HousingType.OTHER)
                .isLandLease(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

