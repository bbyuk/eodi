package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentPresaleRightSellDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

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
        return null;
    }
}
