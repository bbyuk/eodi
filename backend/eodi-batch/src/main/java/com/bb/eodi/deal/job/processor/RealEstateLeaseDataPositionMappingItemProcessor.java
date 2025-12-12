package com.bb.eodi.deal.job.processor;

import com.bb.eodi.address.domain.dto.AddressPosition;
import com.bb.eodi.address.domain.dto.RoadNameAddressQueryParameter;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.legaldong.domain.dto.LegalDongInfoDto;
import com.bb.eodi.legaldong.domain.repository.LegalDongCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 부동산 임대차 실거래가 데이터 좌표 매핑 ItemProcessor
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateLeaseDataPositionMappingItemProcessor implements ItemProcessor<RealEstateLease, RealEstateLease> {

    private final RoadNameAddressRepository roadNameAddressRepository;
    private final LegalDongCacheRepository legalDongCacheRepository;

    @Override
    public RealEstateLease process(RealEstateLease item) throws Exception {
        // 정상 지번이 없을 경우 수기 후보정 처리
        if (!item.hasCorrectLandLot()) {
            return null;
        }

        // 아파트, 오피스텔 건만 우선적용
        if (!item.getHousingType().equals(HousingType.APT) && !item.getHousingType().equals(HousingType.OFFICETEL)) {
            return null;
        }

        /**
         * 1. 부동산 임대차 실거래가 real_estate_lease(region_id, legal_dong_name) -> 법정동 legal_dong
         * 2. 법정동 legal_dong(code)
         *          + 부동산 임대차 실거래가 real_estate_lease(land_lot_main_no, land_lot_sub_no)
         *          + 지번주소 land_lot_address(is_representative) = '1'
         *              -> 지번주소 land_lot_address
         * 3. 지번주소 land_lot_address(manage_no) -> 도로명주소 road_name_address
         * 4. 법정동 legal_dong(code)
         *          + 도로명주소 road_name_address(road_name_code, building_main_no, building_sub_no, is_underground)
         *              -> 주소위치정보 address_position
         */
        LegalDongInfoDto targetLegalDongInfo = legalDongCacheRepository.findLegalDongInfoById(item.getRegionId())
                .orElseThrow(() -> new RuntimeException("대상 지역 법정동 정보를 찾지 못했습니다."))
                .findSubtreeNode(item.getLegalDongName())
                .orElseThrow(() -> {
                    log.error("매핑 실패 -> {}", item.getLegalDongName());
                    return new RuntimeException("대상 법정동 정보를 찾지 못했습니다.");
                });

        List<AddressPosition> addressPositions = roadNameAddressRepository.findAddressPositions(RoadNameAddressQueryParameter
                .builder()
                .legalDongCode(targetLegalDongInfo.getCode())
                .landLotMainNo(item.getLandLotMainNo())
                .landLotSubNo(item.getLandLotSubNo())
                .build());

        if (addressPositions.isEmpty() || addressPositions.size() > 1) {
            log.error("매핑이 안되는 경우");
            return null;
        }

        item.mappingPos(addressPositions.get(0).getXPos(), addressPositions.get(0).getYPos());
        return item;
    }
}
