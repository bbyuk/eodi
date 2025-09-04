package com.bb.eodi.batch.job.deal.load.writer;

import com.bb.eodi.batch.domain.deal.entity.Sale;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * 아파트 매매 데이터 적재 배치 writer
 */
public class ApartmentSaleRowWriter implements ItemWriter<Sale> {

    @Override
    public void write(Chunk<? extends Sale> chunk) throws Exception {

    }
}
