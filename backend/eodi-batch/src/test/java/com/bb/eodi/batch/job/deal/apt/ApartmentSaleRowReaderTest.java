package com.bb.eodi.batch.job.deal.apt;

import org.junit.jupiter.api.Test;

/**
 * 아파트 매매 데이터 reader test
 */

class ApartmentSaleRowReaderTest {
    @Test
    void testReadCsv() throws Exception {
        ApartmentSaleRowReader reader = new ApartmentSaleRowReader("seoul", "annual", "C:/Users/User/Desktop/private/workspace/eodi/bootstrap/data");

        ApartmentSaleRow dto;
        while ((dto = reader.read()) != null) {
            System.out.println(dto);
        }
    }
}