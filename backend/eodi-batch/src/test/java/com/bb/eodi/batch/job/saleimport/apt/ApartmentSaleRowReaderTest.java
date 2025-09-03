package com.bb.eodi.batch.job.saleimport.apt;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

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