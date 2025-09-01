package com.bb.eodi.batch.job.saleimport.apt;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 아파트 매매 데이터 import job processor
 */
public class ApartmentSaleRowReader implements ItemReader<ApartmentSaleRow> {
    @Override
    public ApartmentSaleRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        /**
         * 데이터 read
         */
        return null;
    }
}
