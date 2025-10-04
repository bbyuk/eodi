package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedLeaseDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;

/**
 * 단독/다가구주택 임대차 데이터 ItemReader 임시클래스
 */
public class MultiUnitDetachedLeaseDataItemReader extends RealEstateDealDataItemStreamReader<MultiUnitDetachedLeaseDataItem> {

    public MultiUnitDetachedLeaseDataItemReader(Path tempFilePath) {
        super(tempFilePath);
    }

    @Override
    public MultiUnitDetachedLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
