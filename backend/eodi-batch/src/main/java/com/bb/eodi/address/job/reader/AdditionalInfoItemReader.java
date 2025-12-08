package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.AdditionalInfoItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 주소DB 부가정보 ItemReader
 */
@Slf4j
public class AdditionalInfoItemReader extends AbstractAddressItemStreamReader<AdditionalInfoItem> {


    public AdditionalInfoItemReader(String filePath) {
        super(filePath);
    }

    @Override
    public AdditionalInfoItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] split = nextLine("\\|");

        if (split == null) return null;

        return AdditionalInfoItem.builder()
                .manageNo(split[0])
                .admDongCode(split[1])
                .admDongName(split[2])
                .zipNo(split[3])
                .zipNoSeq(split[4])
                .multipleDeliveryName(split[5])
                .buildingName(split[6])
                .sigunguBuildingName(split[7])
                .isMultiplex(split[8])
                .build();
    }
}
