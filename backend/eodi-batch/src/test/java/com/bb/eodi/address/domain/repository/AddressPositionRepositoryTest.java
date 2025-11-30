package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.AddressPositionFindQuery;
import com.bb.eodi.address.domain.entity.AddressPosition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("medium - 주소위치 Repository db 연동 테스트")
class AddressPositionRepositoryTest {

    @Autowired
    AddressPositionRepository addressPositionRepository;

    @Test
    @DisplayName("주소 위치 정보 조회 테스트")
    void testFindAddressPosition() throws Exception {
        // given
        String roadNameCode = "481213327042";
        String legalDongCode = "4812110100";
        int buildingMainNo = 23;
        int buildingSubNo = 6;
        String isUnderground = "0";

        // when
        Optional<AddressPosition> result = addressPositionRepository.findAddressPosition(
                AddressPositionFindQuery.builder()
                        .roadNameCode(roadNameCode)
                        .legalDongCode(legalDongCode)
                        .buildingMainNo(buildingMainNo)
                        .buildingSubNo(buildingSubNo)
                        .isUnderground(isUnderground)
                        .build()
        );

        // then
        Assertions.assertThat(result.isPresent()).isTrue();

    }
}