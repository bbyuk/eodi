package com.bb.eodi.batch.job.saleimport.apt;

import com.bb.eodi.batch.domain.deal.entity.Sale;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * 아파트 매매 데이터 import job writer
 */
public class ApartmentSaleRowWriter implements ItemWriter<Sale> {

    @Override
    public void write(Chunk<? extends Sale> chunk) throws Exception {

    }
}
