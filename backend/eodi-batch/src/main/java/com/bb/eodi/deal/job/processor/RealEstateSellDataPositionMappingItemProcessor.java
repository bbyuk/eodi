package com.bb.eodi.deal.job.processor;

import com.bb.eodi.address.domain.dto.AddressPositionFindQuery;
import com.bb.eodi.address.domain.dto.AddressPositionIdentifier;
import com.bb.eodi.address.domain.dto.LandLotAddressFindQuery;
import com.bb.eodi.address.domain.entity.AddressPosition;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.AddressPositionRepository;
import com.bb.eodi.address.domain.repository.LandLotAddressRepository;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.legaldong.domain.dto.LegalDongInfoDto;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 부동산 매매 실거래가 데이터 좌표 매핑 ItemProcessor
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateSellDataPositionMappingItemProcessor implements ItemProcessor<RealEstateSell, RealEstateSell> {

    private final AddressPositionRepository addressPositionRepository;
    private final RoadNameAddressRepository roadNameAddressRepository;
    private final LandLotAddressRepository landLotAddressRepository;

    private final LegalDongCacheRepository legalDongCacheRepository;

    @Override
    public RealEstateSell process(RealEstateSell item) throws Exception {
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
                .orElseThrow(() -> new RuntimeException("대상 법정동 정보를 찾지 못했습니다."));

        String addressManageNo = landLotAddressRepository.findRepresentativeLandLotAddressManageNo(LandLotAddressFindQuery
                        .builder()
                        .legalDongCode(targetLegalDongInfo.getCode())
                        .landLotMainNo(item.getLandLotMainNo())
                        .landLotSubNo(item.getLandLotSubNo())
                        .build())
                .orElseThrow(() -> new RuntimeException("주소 관리번호를 찾지 못했습니다."));

        RoadNameAddress roadNameAddress = roadNameAddressRepository.findByManageNo(addressManageNo)
                .orElseThrow(() -> new RuntimeException("주소 관리번호에 해당하는 도로명주소를 찾지 못했습니다."));

        AddressPosition addressPosition = addressPositionRepository.findAddressPosition(
                        AddressPositionFindQuery.builder()
                                .roadNameCode(roadNameAddress.getRoadNameCode())
                                .legalDongCode(targetLegalDongInfo.getCode())
                                .isUnderground(roadNameAddress.getIsUnderground())
                                .buildingMainNo(roadNameAddress.getBuildingMainNo())
                                .buildingSubNo(roadNameAddress.getBuildingSubNo())
                                .build()
                )
                .orElseThrow(() -> {
                    log.debug("아오");
                    throw new RuntimeException("주소 위치 정보를 찾지 못했습니다.");
                });

        item.mappingPos(addressPosition.getXPos(), addressPosition.getYPos());

        return item;
    }
}
