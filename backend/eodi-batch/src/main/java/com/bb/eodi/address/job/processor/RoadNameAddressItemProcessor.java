package com.bb.eodi.address.job.processor;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 도로명주소 ItemProcessor
 *
 * @return 도로명주소 ItemProcessor
 */
@Component
@StepScope
public class RoadNameAddressItemProcessor implements ItemProcessor<RoadNameAddressItem, RoadNameAddress> {

    @Override
    public RoadNameAddress process(RoadNameAddressItem item) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        return RoadNameAddress.builder()
                .manageNo(item.getManageNo())
                .legalDongCode(item.getLegalDongCode())
                .sidoName(item.getSidoName())
                .sigunguName(item.getSigunguName())
                .umdName(item.getUmdName())
                .riName(item.getRiName())
                .isMountain(item.getIsMountain())
                .landLotMainNo(StringUtils.hasText(item.getLandLotMainNo()) ? Integer.parseInt(item.getLandLotMainNo()) : 0)
                .landLotSubNo(StringUtils.hasText(item.getLandLotSubNo()) ? Integer.parseInt(item.getLandLotSubNo()) : 0)
                .roadNameCode(item.getRoadNameCode())
                .roadName(item.getRoadName())
                .isUnderground(item.getIsUnderground())
                .buildingMainNo(StringUtils.hasText(item.getBuildingMainNo()) ? Integer.parseInt(item.getBuildingMainNo()) : 0)
                .buildingSubNo(StringUtils.hasText(item.getBuildingSubNo()) ? Integer.parseInt(item.getBuildingSubNo()) : 0)
                .admDongCode(item.getAdmDongCode())
                .admDongName(item.getAdmDongName())
                .basicDistrictNo(item.getBasicDistrictNo())
                .beforeRoadNameAddress(item.getBeforeRoadNameAddress())
                .effectStartDate(item.getEffectStartDate())
                .isMulti(item.getIsMulti())
                .updateReasonCode(item.getUpdateReasonCode())
                .buildingName(item.getBuildingName())
                .sigunguBuildingName(item.getSigunguBuildingName())
                .remark(item.getRemark())
                .createdAt(
                        !StringUtils.hasText(item.getUpdateReasonCode())
                                || "31".equals(item.getUpdateReasonCode())
                                ? now
                                : null)
                .updatedAt(now)
                .build();
    }
}
