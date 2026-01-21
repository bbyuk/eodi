package com.bb.eodi.address.job.writer;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 주소위치정보 ItemWriter
 * @return 주소위치정보 ItemWriter
 */
@Component
@StepScope
@RequiredArgsConstructor
public class AddressPositionUpdateItemWriter implements ItemWriter<RoadNameAddress> {
    private final RoadNameAddressRepository roadNameAddressRepository;

    @Override
    public void write(Chunk<? extends RoadNameAddress> chunk) throws Exception {
        roadNameAddressRepository.updatePosition(chunk.getItems());
    }
}
