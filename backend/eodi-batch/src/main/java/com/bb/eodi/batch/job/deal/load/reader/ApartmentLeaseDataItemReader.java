package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentLeaseDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;

/**
 * 아파트 임대차 데이터 ItemReader 임시 클래스
 */
public class ApartmentLeaseDataItemReader extends RealEstateDealDataItemStreamReader<ApartmentLeaseDataItem> {

    public ApartmentLeaseDataItemReader(Path tempFilePath) {
        super(tempFilePath);
    }

    @Override
    public ApartmentLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
