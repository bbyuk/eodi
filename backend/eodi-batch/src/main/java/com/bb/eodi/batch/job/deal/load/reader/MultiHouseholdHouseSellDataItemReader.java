package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiHouseholdHouseSellDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;

/**
 * 연립/다세대주택 매매 데이터 ItemReader 임시 클래스
 */
public class MultiHouseholdHouseSellDataItemReader extends RealEstateDealDataItemStreamReader<MultiHouseholdHouseSellDataItem> {
    public MultiHouseholdHouseSellDataItemReader(Path path) {
        super(path);
    }

    @Override
    public MultiHouseholdHouseSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
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
            String buildYear = values[14];
            // 전용면적
            String excluUseAr = values[6];
            // 대지권면적
            String landAr = values[7];
            // 계약년도
            String dealYear = values[8].substring(0, 4);
            // 계약월
            String dealMonth = values[8].substring(4, 6);
            // 계약일
            String dealDay = values[9];
            // 거래금액(만원)
            String dealAmount = values[10];
            // 층
            String floor = values[11];
            // 해제여부
            String cdealType;
            // 해제사유발생일
            String cdealDay = values[16];
            // 거래유형(중개 및 직거래 여부)
            String dealingGbn = values[17];
            // 중개사소재지(시군구 단위)
            String estateAgentSggNm = values[18];
            // 등기일자
            String rgstDate = values[19];
            // 거래주체정보_매도자(개인/법인/공공기관/기타)
            String slerGbn = values[13];
            // 거래주체정보_매수자(개인/법인/공공기관/기타)
            String buyerGbn = values[12];
            String tempSggNm = values[1];

            return new MultiHouseholdHouseSellDataItem(
                    null,
                    null,
                    mhouseNm,
                    jibun,
                    buildYear,
                    excluUseAr,
                    landAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    dealAmount,
                    floor,
                    null,
                    cdealDay,
                    dealingGbn,
                    estateAgentSggNm,
                    rgstDate,
                    slerGbn,
                    buyerGbn,
                    tempSggNm
            );
        }

    }
}
