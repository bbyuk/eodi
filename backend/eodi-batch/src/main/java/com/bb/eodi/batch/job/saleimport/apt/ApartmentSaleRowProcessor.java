package com.bb.eodi.batch.job.saleimport.apt;

import org.springframework.batch.item.ItemProcessor;

/**
 * 아파트 매매 데이터 import job processor
 */
public class ApartmentSaleRowProcessor implements ItemProcessor<ApartmentSaleRow, ApartmentSaleEntity> {
    @Override
    public ApartmentSaleEntity process(ApartmentSaleRow item) throws Exception {
        // TODO 검증 및 변환 후 리턴
        return null;
    }
}
