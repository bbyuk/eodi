package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentLeaseDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

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

        String[] split = Arrays.stream(line.split(","))
                .map(elem -> elem.replace("\"", ""))
                .toArray(String[]::new);

        // 법정동코드 상위 5자리
        String sggCd;
        // 법정동 명
        String umdNm;
        // 단지명
        String aptNm = split[5];
        // 지번
        String jibun = split[2];
        // 전용면적
        String excluUseAr = split[7];
        // 계약년도
        String dealYear = split[8].substring(0, 4);
        // 계약월
        String dealMonth = split[8].substring(4, 6);
        // 계약일
        String dealDay = split[9];
        // 보증금액(만원)
        String deposit = split[10];
        // 월세금액(만원)
        String monthlyRent = split[11];
        // 층
        String floor = split[12];
        // 건축년도
        String buildYear = split[13];
        // 계약기간
        String contractTerm = split[15];
        // 계약구분
        String contractType = split[16];
        // 갱신요구권사용
        String useRRRight = split[17];
        // 종전계약보증금
        String preDeposit = split[18];
        // 종전계약월세
        String preMonthlyRent = split[19];
        String tempSggNm = split[1];

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
