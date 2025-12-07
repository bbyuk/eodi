package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.LandLotAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.deal.domain.eunms.MonthlyDealDataLoadJobKey.CURRENT_INDEX;

/**
 * 지번 주소 ItemReader
 */
@Slf4j
public class LandLotAddressItemReader extends AbstractAddressItemStreamReader<LandLotAddressItem> {

    public LandLotAddressItemReader(String filePath) {
        super(filePath);
    }

    @Override
    public LandLotAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = nextLine("\\|");

        if (line == null) return null;

        return LandLotAddressItem.builder()
                .manageNo(line[0])
                .seq(line[1])
                .legalDongCode(line[2])
                .sidoName(line[3])
                .sigunguName(line[4])
                .legalUmdName(line[5])
                .legalRiName(line[6])
                .isMountain(line[7])
                .landLotMainNo(line[8])
                .landLotSubNo(line[9])
                .isRepresentative(line[10])
                .build();
    }
}
