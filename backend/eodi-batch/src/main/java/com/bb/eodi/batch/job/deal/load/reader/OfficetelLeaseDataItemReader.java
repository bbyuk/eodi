package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.OfficetelLeaseDataItem;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;

/**
 * 오피스텔 임대차 데이터 ItemReader 임시클래스
 */
public class OfficetelLeaseDataItemReader extends RealEstateDealDataItemStreamReader<OfficetelLeaseDataItem> {
    public OfficetelLeaseDataItemReader(Path path) {
        super(path);
    }

    @Override
    public OfficetelLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
