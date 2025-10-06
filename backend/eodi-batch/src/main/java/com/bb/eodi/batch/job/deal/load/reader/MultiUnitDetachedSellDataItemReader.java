package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedSellDataItem;
import com.opencsv.CSVReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.StringReader;
import java.nio.file.Path;

/**
 * 단독/다가구 주택 데이터 ItemReader 임시 클래스
 */
public class MultiUnitDetachedSellDataItemReader extends RealEstateDealDataItemStreamReader<MultiUnitDetachedSellDataItem> {
    public MultiUnitDetachedSellDataItemReader(Path path) {
        super(path);
    }

    @Override
    public MultiUnitDetachedSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
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
            // 주택유형(단독/다가구)
            String houseType = values[3];
            // 지번
            String jibun = values[2];
            // 연면적
            String totalFloorAr = values[5];
            // 대지면적
            String plottageAr = values[6];
            // 계약년도
            String dealYear = values[7].substring(0, 4);
            // 계약월
            String dealMonth = values[7].substring(4, 6);
            // 계약일
            String dealDay = values[8];
            // 거래금액(만원)
            String dealAmount = values[9];
            // 건축년도
            String buildYear = values[12];
            // 해제여부
            String cdealType;
            // 해제사유발생일
            String cdealDay = values[14];
            // 거래유형(중개 및 직거래 여부)
            String dealingGbn = values[15];
            // 중개사소재지(시군구 단위)
            String estateAgentSggNm = values[16];
            // 거래주체정보_매도자(개인/법인/공공기관/기타)
            String slerGbn = values[14];
            //거래주체정보_매수자(개인/법인/공공기관/기타)
            String buyerGbn = values[13];
            String tempSggNm = values[1];

            return new MultiUnitDetachedSellDataItem(
                    null,
                    null,
                    houseType,
                    jibun,
                    totalFloorAr,
                    plottageAr,
                    dealYear,
                    dealMonth,
                    dealDay,
                    dealAmount,
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
