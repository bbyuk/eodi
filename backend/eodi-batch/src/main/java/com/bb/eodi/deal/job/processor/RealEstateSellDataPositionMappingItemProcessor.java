package com.bb.eodi.deal.job.processor;

import com.bb.eodi.address.domain.repository.AddressPositionRepository;
import com.bb.eodi.address.domain.repository.BuildingAddressRepository;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.legaldong.domain.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

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

        /**
         * 매매데이터.지번 + 매매데이터.법정동코드 -> 건물주소.지번 -> 매매데이터.건물관리번호
         * 매매데이터.건물관리번호 + 주소좌표정보.좌표 -> 매매데이터.좌표
         */



        return item;
    }
}
