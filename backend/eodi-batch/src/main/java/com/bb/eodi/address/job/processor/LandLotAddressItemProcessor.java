package com.bb.eodi.address.job.processor;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

/**
 * 지번주소 ItemProcessor
 *
 * @return 지번주소 ItemProcessor
 */
public class LandLotAddressItemProcessor implements ItemProcessor<LandLotAddressItem, LandLotAddress> {


    @Override
    public LandLotAddress process(LandLotAddressItem item) throws Exception {
        return LandLotAddress.builder()
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
                .isUnderground(item.getIsUnderground())
                .buildingMainNo(StringUtils.hasText(item.getBuildingMainNo()) ? Integer.parseInt(item.getBuildingMainNo()) : 0)
                .buildingSubNo(StringUtils.hasText(item.getBuildingSubNo()) ? Integer.parseInt(item.getBuildingSubNo()) : 0)
                .updateReasonCode(item.getUpdateReasonCode())
                .build();
    }
}
