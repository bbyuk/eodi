package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentPresaleRightSellDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;

/**
 * 아파트 분양권/입주권 데이터 ItemReader 임시 클래스
 */
public class ApartmentPresaleRightSellDataItemReader extends RealEstateDealDataItemStreamReader<ApartmentPresaleRightSellDataItem> {

    public ApartmentPresaleRightSellDataItemReader(Path tempFilePath) {
        super(tempFilePath);
    }

    @Override
    public ApartmentPresaleRightSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
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
            // 단지 명
            String aptNm = values[3];
            // 지번
            String jibun = values[2];
            // 전용면적
            String excluUseAr = values[4];
            // 계약년도
            String dealYear = values[9].substring(0, 4);
            // 계약월
            String dealMonth = values[9].substring(4, 6);
            // 계약일
            String dealDay = values[10];
            // 거래금액(만원)
            String dealAmount = values[5];
            // 구분
            String ownershipGbn = values[11];
            // 층
            String floor = values[6];
            // 해제여부
            String cdealType;
            // 해제사유발생일
            String cdealDay = values[12];
            // 거래유형(중개 및 직거래 여부)
            String dealingGbn = values[13];
            // 중개사소재지(시군구 단위)
            String estateAgentSggNm = values[14];
            // 거래주체정보_매도자(개인/법인/공공기관/기타)
            String slerGbn = values[8];
            // 거래주체정보_매수자(개인/법인/공공기관/기타)
            String buyerGbn = values[7];
            String tempSggNm = values[1];



            return new ApartmentPresaleRightSellDataItem(
                    null,
                    null,
                    null,
                    aptNm,
                    jibun,
                    excluUseAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    dealAmount,
                    ownershipGbn,
                    floor,
                    "",
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
