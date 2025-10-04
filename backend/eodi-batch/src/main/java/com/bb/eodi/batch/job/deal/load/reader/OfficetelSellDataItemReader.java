package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.OfficetelSellDataItem;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.nio.file.Path;

public class OfficetelSellDataItemReader extends RealEstateDealDataItemStreamReader<OfficetelSellDataItem> {
    public OfficetelSellDataItemReader(Path path) {
        super(path);
    }

    @Override
    public OfficetelSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
