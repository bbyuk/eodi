package com.bb.eodi.batch.job.deal.load.reader;


import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;

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

        String[] split = line.split(",");

        return null;
    }
}
