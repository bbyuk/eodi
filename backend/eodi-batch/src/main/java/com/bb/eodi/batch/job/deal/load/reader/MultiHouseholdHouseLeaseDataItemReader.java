package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiHouseholdHouseLeaseDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;

/**
 * 연립/다세대주택 임대차 데이터 ItemReader 임시클래스
 */
public class MultiHouseholdHouseLeaseDataItemReader extends RealEstateDealDataItemStreamReader<MultiHouseholdHouseLeaseDataItem> {
    public MultiHouseholdHouseLeaseDataItemReader(Path path) {
        super(path);
    }

    @Override
    public MultiHouseholdHouseLeaseDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
