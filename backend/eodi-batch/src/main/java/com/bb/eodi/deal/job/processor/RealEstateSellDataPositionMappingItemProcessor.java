package com.bb.eodi.deal.job.processor;

import com.bb.eodi.address.domain.dto.AddressPositionFindQuery;
import com.bb.eodi.address.domain.dto.AddressPositionIdentifier;
import com.bb.eodi.address.domain.dto.BuildingAddressFindQuery;
import com.bb.eodi.address.domain.entity.AddressPosition;
import com.bb.eodi.address.domain.entity.BuildingAddress;
import com.bb.eodi.address.domain.repository.AddressPositionRepository;
import com.bb.eodi.address.domain.repository.BuildingAddressRepository;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.legaldong.domain.entity.LegalDong;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    private final BuildingAddressRepository buildingAddressRepository;

    private final LegalDongRepository legalDongRepository;

    @Override
    public RealEstateSell process(RealEstateSell item) throws Exception {
        // 정상 지번이 없을 경우 수기 후보정 처리
        if (!item.hasCorrectLandLot()) return null;

        // 아파트, 오피스텔 건만 우선적용
        if (!item.getHousingType().equals(HousingType.APT) && !item.getHousingType().equals(HousingType.OFFICETEL)) {
            return null;
        }

        /**
         * 매매데이터.지번 + 매매데이터.법정동코드 -> 건물주소.지번 -> 매매데이터.건물관리번호
         * 매매데이터.건물관리번호 + 주소좌표정보.좌표 -> 매매데이터.좌표
         */
        LegalDong regionLegalDong = legalDongRepository.findById(item.getRegionId())
                .orElseThrow(() -> new RuntimeException("해당 지역 법정동을 찾지 못했습니다."));

        LegalDong targetLegalDong = legalDongRepository.findBySidoCodeAndSigunguCodeAndLegalDongName(
                        regionLegalDong.getSidoCode(),
                        regionLegalDong.getSigunguCode(),
                        regionLegalDong.getName() + " " + item.getLegalDongName())
                .orElseThrow(() -> new RuntimeException("대상 법정동을 찾지 못했습니다."));

        Set<AddressPositionIdentifier> addressPositionIdentifierSet = buildingAddressRepository.findBuildingAddress(
                        BuildingAddressFindQuery
                                .builder()
                                .legalDongCode(targetLegalDong.getCode())
                                .landLotMainNo(item.getLandLotMainNo())
                                .landLotSubNo(item.getLandLotSubNo())
                                .isMountain(item.getIsMountain() ? "1" : "0")
                                .build())
                .stream()
                .map(buildingAddress -> AddressPositionIdentifier
                        .builder()
                        .roadNameCode(buildingAddress.getRoadNameCode())
                        .legalDongCode(buildingAddress.getLegalDongCode().replaceFirst("..$", "00"))
                        .isUnderground(buildingAddress.getIsUnderground())
                        .buildingMainNo(buildingAddress.getBuildingMainNo())
                        .buildingSubNo(buildingAddress.getBuildingSubNo())
                        .build())
                .collect(Collectors.toSet());

        // 대상 건물주소의 주소위치 identifier 매핑이 여러건인 경우 수기매핑
        if (addressPositionIdentifierSet.size() > 1 || addressPositionIdentifierSet.isEmpty()) {
            return null;
        }

        AddressPositionIdentifier addressPositionIdentifier = addressPositionIdentifierSet.iterator().next();
        AddressPosition addressPosition = addressPositionRepository.findAddressPosition(
                AddressPositionFindQuery.builder()
                        .roadNameCode(addressPositionIdentifier.getRoadNameCode())
                        .legalDongCode(addressPositionIdentifier.getLegalDongCode())
                        .isUnderground(addressPositionIdentifier.getIsUnderground())
                        .buildingMainNo(addressPositionIdentifier.getBuildingMainNo())
                        .buildingSubNo(addressPositionIdentifier.getBuildingSubNo())
                        .build()
        ).orElseThrow(() -> {
            log.error(";;");
            return new RuntimeException("주소 위치정보를 찾지못했습니다.");
        });

        item.mappingPos(addressPosition.getXPos(), addressPosition.getYPos());
        return item;
    }
}
