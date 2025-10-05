package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedLeaseDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;

/**
 * 단독/다가구주택 임대차 데이터 ItemReader 임시클래스
 */
public class MultiUnitDetachedLeaseDataItemReader extends RealEstateDealDataItemStreamReader<MultiUnitDetachedLeaseDataItem> {

    public MultiUnitDetachedLeaseDataItemReader(Path tempFilePath) {
        super(tempFilePath);
    }

    @Override
    public MultiUnitDetachedLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = nextLine();
        if (line == null) {
            return null;
        }

        try (CSVReader reader = new CSVReader(new StringReader(line))) {
            String[] values = reader.readNext();

            // 지역코드
            String sggCd;
            // 법정동 명
            String umdNm;
            // 연면적
            String totalFloorAr = values[4];
            // 계약년도
            String dealYear = values[6].substring(0, 4);
            // 계약월
            String dealMonth = values[6].substring(4, 6);
            // 계약일
            String dealDay = values[7];
            // 보증금액(만원)
            String deposit = values[8];
            // 주택 타입
            String houseType = values[17];
            // 월세금액(만원)
            String monthlyRent = values[9];
            // 건축년도
            String buildYear = values[10];
            // 계약기간
            String contractTerm = values[12];
            // 계약구분
            String contractType = values[13];
            // 갱신요구권사용
            String useRRRight = values[14];
            // 종전계약보증금
            String preDeposit = values[15];
            // 종전계약월세
            String preMonthlyRent = values[16];
            String tempSggNm = values[1];

            return new MultiUnitDetachedLeaseDataItem(
                    null,
                    null,
                    totalFloorAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    deposit,
                    houseType,
                    monthlyRent,
                    buildYear,
                    contractTerm,
                    contractType,
                    useRRRight,
                    preDeposit,
                    preMonthlyRent,
                    tempSggNm
            );
        }
    }
}
