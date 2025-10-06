package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiHouseholdHouseLeaseDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;

/**
 * 연립/다세대주택 임대차 데이터 ItemReader 임시클래스
 */
public class MultiHouseholdHouseLeaseDataItemReader extends RealEstateDealDataItemStreamReader<MultiHouseholdHouseLeaseDataItem> {
    public MultiHouseholdHouseLeaseDataItemReader(Path path) {
        super(path);
    }

    @Override
    public MultiHouseholdHouseLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
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
            // 연립다세대명
            String mhouseNm = values[5];
            // 지번
            String jibun = values[2];
            // 건축년도
            String buildYear = values[13];
            // 전용면적
            String excluUseAr = values[7];
            // 계약년도
            String dealYear = values[8].substring(0, 4);
            // 계약월
            String dealMonth = values[8].substring(4, 6);
            // 계약일
            String dealDay = values[9];
            // 보증금액(만원)
            String deposit = values[10];
            // 월세금액(만원)
            String monthlyRent = values[11];
            // 층
            String floor = values[12];
            // 계약기간
            String contractTerm = values[15];
            // 계약구분
            String contractType = values[16];
            // 갱신요구권사용
            String useRRRight = values[17];
            // 종전계약보증금
            String preDeposit = values[18];
            // 종전계약월세
            String preMonthlyRent = values[19];
            String tempSggNm = values[1];



            return new MultiHouseholdHouseLeaseDataItem(
                    null,
                    null,
                    mhouseNm,
                    jibun,
                    buildYear,
                    excluUseAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    deposit,
                    monthlyRent,
                    floor,
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
