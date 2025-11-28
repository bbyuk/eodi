package com.bb.eodi.address.domain.repository;

import com.bb.eodi.address.domain.dto.BuildingAddressFindQuery;
import com.bb.eodi.address.domain.entity.BuildingAddress;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("medium - 건물주소 Repository DB 연동 테스트")
class BuildingAddressRepositoryTest {

    @Autowired
    BuildingAddressRepository buildingAddressRepository;

    @Test
    @DisplayName("조회 쿼리 파라미터에 해당하는 건물주소를 조회한다.")
    void testFindBuildingAddress() throws Exception {
        // given
        String legalDongCode = "2611010100";
        int landLotMainNo = 60;
        int landLotSubNo = 18;
        String isMountain = "0";

        BuildingAddressFindQuery query = BuildingAddressFindQuery.builder()
                .legalDongCode(legalDongCode)
                .landLotMainNo(landLotMainNo)
                .landLotSubNo(landLotSubNo)
                .isMountain(isMountain)
                .build();

        // when
        List<BuildingAddress> buildingAddress = buildingAddressRepository.findBuildingAddress(query);

        // then
        Assertions.assertThat(buildingAddress).isNotEmpty();
        Assertions.assertThat(buildingAddress.get(0).getId()).isEqualTo(1L);
    }

}