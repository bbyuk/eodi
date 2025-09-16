package com.bb.eodi.batch.job.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentPreSaleRightSellDataItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

/**
 * 아파트 분양권 매매 데이터 ItemReader
 */
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentPresaleRightSellDataItemReader implements ItemStreamReader<ApartmentPreSaleRightSellDataItem> {

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ItemStreamReader.super.open(executionContext);
    }

    @Override
    public ApartmentPreSaleRightSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }

    @Override
    public void close() throws ItemStreamException {
        ItemStreamReader.super.close();
    }


}
