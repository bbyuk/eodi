package com.bb.eodi.batch.job.legaldong.load.reader;

import com.bb.eodi.batch.job.legaldong.load.model.LegalDongRow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegalDongRowReaderTest {

    @Test
    @DisplayName("[small] 법정동 코드 적재 배치 reader 단위 테스트")
    void testLegalDongLoadReader() throws Exception {
        // given
        LegalDongRowReader reader = new LegalDongRowReader("C:/Users/User/Desktop/private/workspace/eodi/bootstrap/data");

        // when
        LegalDongRow row;
        while((row = reader.read()) != null) {
            System.out.println("row = " + row);
        }

        // then
    }
}