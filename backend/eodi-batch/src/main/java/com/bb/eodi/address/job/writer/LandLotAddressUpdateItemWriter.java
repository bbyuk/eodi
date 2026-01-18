package com.bb.eodi.address.job.writer;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.repository.LandLotAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class LandLotAddressUpdateItemWriter implements ItemWriter<LandLotAddress> {

    private final LandLotAddressRepository landLotAddressRepository;

    @Override
    public void write(Chunk<? extends LandLotAddress> chunk) throws Exception {
        List<LandLotAddress> insertList = new ArrayList<>();
        List<LandLotAddress> updateList = new ArrayList<>();
        List<LandLotAddress> deleteList = new ArrayList<>();

        for (LandLotAddress item : chunk.getItems()){
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

        landLotAddressRepository.insertBatch(insertList);
        landLotAddressRepository.updateBatch(updateList);
        landLotAddressRepository.deleteBatch(deleteList);
    }
}
