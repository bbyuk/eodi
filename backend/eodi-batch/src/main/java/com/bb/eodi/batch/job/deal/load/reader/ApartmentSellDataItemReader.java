package com.bb.eodi.batch.job.deal.load.reader;


import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 아파트 매매 데이터 ItemReader 임시 클래스
 */
public class ApartmentSellDataItemReader extends RealEstateDealDataItemStreamReader<ApartmentSellDataItem> {

    public ApartmentSellDataItemReader(Path tempFilePath) {
        super(tempFilePath);
    }

    @Override
    public ApartmentSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = nextLine();
        if (line == null) {
            return null;
        }

        try (CSVReader reader = new CSVReader(new StringReader(line))) {
            String[] values = reader.readNext();

            String sggCd;
            // 법정동 명
            String umdNm;
            // 단지명
            String aptNm = values[5];
            // 지번
            String jibun = values[2];
            // 전용면적
            String excluUseAr = values[6];

            // 계약년도
            String dealYear = values[7].substring(0, 4);
            // 계약월
            String dealMonth = values[7].substring(4, 6);
            // 계약일
            String dealDay = values[8];
            // 거래금액(만원)
            String dealAmount = values[9];
            // 층
            String floor = values[11];
            // 건축년도,
            String buildYear = values[14];
            // 해제여부,
            String cdealType;
            // 해제사유발생일
            String cdealDay = values[16];
            // 거래유형(중개 및 직거래 여부)
            String dealingGbn = values[17];
            // 중개사소재지(시군구 단위)
            String estateAgentSggNm = values[18];
            // 등기일자(rgstDate)
            String rgstDate = values[19];
            // 아파트 동명
            String aptDong = values[10];
            // 거래주체정보_매도자(개인/법인/공공기관/기타)
            String slerGbn = values[12];
            // 거래주체정보_매수자(개인/법인/공공기관/기타)
            String buyerGbn = values[13];
            // 토지임대부 아파트 여부
            String landLeaseholdGbn;
            String tempSggNm = values[1];

            return new ApartmentSellDataItem(
                    null,
                    null,
                    aptNm,
                    jibun,
                    excluUseAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    dealAmount,
                    floor,
                    buildYear,
                    null,
                    cdealDay,
                    dealingGbn,
                    estateAgentSggNm,
                    rgstDate,
                    aptDong,
                    slerGbn,
                    buyerGbn,
                    null,
                    tempSggNm
            );
        }
    }
}
