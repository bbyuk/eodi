package com.bb.eodi.deal.domain.port;

import com.bb.eodi.deal.job.dto.ApartmentSellDataItem;
import com.bb.eodi.integration.gov.deal.dto.DealDataQuery;
import com.bb.eodi.integration.gov.deal.dto.DealDataResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("medium - 실거래가 데이터 API 연동 테스트")
class DealDataPortTest {

    @Autowired
    DealDataPort dealDataPort;


    @Test
    @DisplayName("medium - 아파트 매매 데이터 API 테스트")
    public void testApartmentSellApi() throws Exception {
        // given
        String seoulSeongDonggu = "11200";
        String dealMonth = "202510";
        int pageSize = 20;
        int pageNum = 1;
        DealDataQuery query = new DealDataQuery(seoulSeongDonggu, dealMonth, pageSize, pageNum);
        // when
        DealDataResponse<ApartmentSellDataItem> apartmentSellData = dealDataPort.getApartmentSellData(query);

        // then
        System.out.println("header = " + apartmentSellData.header());
        System.out.println("body = " + apartmentSellData.body());
    }

}