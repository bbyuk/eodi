package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedSellDataItem;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

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
        return null;
    }
}
