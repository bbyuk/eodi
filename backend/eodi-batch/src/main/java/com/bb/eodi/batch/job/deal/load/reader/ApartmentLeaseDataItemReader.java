package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentLeaseDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * 아파트 임대차 데이터 ItemReader 임시 클래스
 */
public class ApartmentLeaseDataItemReader extends RealEstateDealDataItemStreamReader<ApartmentLeaseDataItem> {

    public ApartmentLeaseDataItemReader(Path tempFilePath) {
        super(tempFilePath);
    }

    @Override
    public ApartmentLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = nextLine();
        if (line == null) {
            return null;
        }

        try (CSVReader reader = new CSVReader(new StringReader(line))) {
            String[] values = reader.readNext();

            // 법정동코드 상위 5자리
            String sggCd;
            // 법정동 명
            String umdNm;
            // 단지명
            String aptNm = values[5];
            // 지번
            String jibun = values[2];
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
            // 건축년도
            String buildYear = values[13];
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

            return new ApartmentLeaseDataItem(
                    null,
                    null,
                    aptNm,
                    jibun,
                    excluUseAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    deposit,
                    monthlyRent,
                    floor,
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
