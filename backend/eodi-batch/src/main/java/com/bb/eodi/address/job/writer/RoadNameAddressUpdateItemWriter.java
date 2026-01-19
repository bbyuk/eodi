package com.bb.eodi.address.job.writer;

import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.repository.RoadNameAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 도로명주소 최신화 배치 ItemWriter
 */
@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class RoadNameAddressUpdateItemWriter implements ItemWriter<RoadNameAddress> {

    private final RoadNameAddressRepository roadNameAddressRepository;

    @Override
    public void write(Chunk<? extends RoadNameAddress> chunk) throws Exception {
        List<RoadNameAddress> insertList = new ArrayList<>();
        List<RoadNameAddress> updateList = new ArrayList<>();
        List<RoadNameAddress> deleteList = new ArrayList<>();

        for (RoadNameAddress item : chunk.getItems()) {
            if (item.getUpdateReasonCode().equals("31")) {
                insertList.add(item);
            }
            else if (item.getUpdateReasonCode().equals("34")) {
                updateList.add(item);
            }
            else if (item.getUpdateReasonCode().equals("63")) {
                deleteList.add(item);
            }
        }

        roadNameAddressRepository.insertBatch(insertList);
        roadNameAddressRepository.updateBatch(updateList);
        roadNameAddressRepository.deleteBatch(deleteList);
    }
}
