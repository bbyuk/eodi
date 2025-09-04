package com.bb.eodi.batch.job.deal.apt;

import com.bb.eodi.batch.job.deal.load.model.ApartmentSaleRow;
import com.bb.eodi.batch.job.deal.load.reader.ApartmentSaleRowReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 아파트 매매 데이터 reader test
 */

class ApartmentSaleRowReaderTest {
    
    @Test
    @DisplayName("[small] 아파트 매매 데이터 적재 배치 reader 단위 테스트")
    void testReadCsv() throws Exception {
        ApartmentSaleRowReader reader = new ApartmentSaleRowReader("seoul", "annual", "C:/Users/User/Desktop/private/workspace/eodi/bootstrap/data");

        ApartmentSaleRow dto;
        while ((dto = reader.read()) != null) {
            System.out.println(dto);
        }
    }
}