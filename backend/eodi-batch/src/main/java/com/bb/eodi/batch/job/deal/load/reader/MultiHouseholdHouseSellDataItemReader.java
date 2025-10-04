package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiHouseholdHouseSellDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

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
        return null;
    }
}
