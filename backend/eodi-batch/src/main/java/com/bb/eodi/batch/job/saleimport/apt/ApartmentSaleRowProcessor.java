package com.bb.eodi.batch.job.saleimport.apt;

import com.bb.eodi.batch.domain.entity.Sale;
import org.springframework.batch.item.ItemProcessor;

/**
 * 아파트 매매 데이터 import job processor
 */
public class ApartmentSaleRowProcessor implements ItemProcessor<ApartmentSaleRow, Sale> {
    @Override
    public Sale process(ApartmentSaleRow item) throws Exception {
        // TODO 검증 및 변환 후 리턴
        return null;
    }
}
