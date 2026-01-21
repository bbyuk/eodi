package com.bb.eodi.address.job.processor;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.util.GeoToolsBigDecimalConverter;
import com.bb.eodi.address.job.dto.AddressPositionItem;
import com.bb.eodi.legaldong.domain.dto.LegalDongInfoDto;
import com.bb.eodi.legaldong.domain.repository.LegalDongCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 도로명주소 주소 위치정보 매핑 ItemProcessor
 * @return 도로명주소 주소 위치정보 매핑 ItemProcessor
 */
@StepScope
@Component
@RequiredArgsConstructor
public class AddressPositionMappingItemProcessor implements ItemProcessor<AddressPositionItem, RoadNameAddress> {

    private final LegalDongCacheRepository legalDongCacheRepository;

    @Override
    public RoadNameAddress process(AddressPositionItem item) throws Exception {
        Function<String, BigDecimal> parseBigDecimalWithNull = (str) -> !StringUtils.hasText(str) ? null : new BigDecimal(str);

        if (!StringUtils.hasText(item.getXPos()) || !StringUtils.hasText(item.getYPos())) {
            return null;
        }

        BigDecimal[] wgs84 = GeoToolsBigDecimalConverter.toWgs84(
                parseBigDecimalWithNull.apply(item.getXPos()),
                parseBigDecimalWithNull.apply(item.getYPos())
        );

        LegalDongInfoDto legalDongInfoDto = legalDongCacheRepository.findLegalDongInfoByCode(item.getLegalDongCode())
                .orElse(null);

        if (legalDongInfoDto == null) {
            return null;
        }

        List<String> selectTargetLegalDongCodes = legalDongInfoDto.findSubtreeNodes().stream().map(LegalDongInfoDto::getCode).collect(Collectors.toList());
        selectTargetLegalDongCodes.add(item.getLegalDongCode());

        return RoadNameAddress.builder()
                .manageNo(item.getManageNo())
                .roadNameCode(item.getRoadNameCode())
                .isUnderground(item.getIsUnderground())
                .buildingMainNo(Integer.parseInt(item.getBuildingMainNo()))
                .buildingSubNo(Integer.parseInt(item.getBuildingSubNo()))
                .xPos(wgs84[0])
                .yPos(wgs84[1])
                .build();
    }
}
