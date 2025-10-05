package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.OfficetelSellDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;

public class OfficetelSellDataItemReader extends RealEstateDealDataItemStreamReader<OfficetelSellDataItem> {
    public OfficetelSellDataItemReader(Path path) {
        super(path);
    }

    @Override
    public OfficetelSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = nextLine();

        if (line == null) {
            return null;
        }

        try (CSVReader reader = new CSVReader(new StringReader(line))) {
            String[] values = reader.readNext();
            // 지역코드
            String sggCd;
            // 시군구 명
            String sggNm;
            // 법정동 명
            String umdNm;
            // 지번
            String jibun = values[2];
            // 단지명
            String offiNm = values[5];
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
            String floor = values[10];
            // 건축년도
            String buildYear = values[13];
            // 해제여부
            String cdealType;
            // 해제사유발생일
            String cdealDay = values[15];
            // 거래유형(중개 및 직거래 여부)
            String dealingGbn = values[16];
            // 중개사소재지(시군구 단위)
            String estateAgentSggNm = values[17];
            // 거래주체정보_매도자(개인/법인/공공기관/기타)
            String slerGbn = values[12];
            // 거래주체정보_매수자(개인/법인/공공기관/기타)
            String buyerGbn = values[11];
            String tempSggNm = values[1];


            return new OfficetelSellDataItem(
                    null,
                    null,
                    null,
                    jibun,
                    offiNm,
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
                    slerGbn,
                    buyerGbn,
                    tempSggNm
            );
        }
    }
}
