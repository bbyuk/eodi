package com.bb.eodi.batch.job.address.reader;

import com.bb.eodi.batch.job.address.dto.LandLotAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.batch.job.deal.eunms.MonthlyDealDataLoadJobKey.CURRENT_INDEX;

/**
 * 지번 주소 ItemReader
 */
@Slf4j
public class LandLotAddressItemReader implements ItemStreamReader<LandLotAddressItem> {

    private final Path filePath;
    private BufferedReader br;
    private int readCounter = 0;


    public LandLotAddressItemReader(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(filePath, Charset.forName("MS949"));

            if (executionContext.containsKey(CURRENT_INDEX.name())) {
                int lastIndex = executionContext.getInt(CURRENT_INDEX.name());
                log.info("running from index {}", lastIndex);

                for (int i = 0; i < lastIndex; i++) {
                    br.readLine();
                }

                this.readCounter = lastIndex;
            }
            else {
                this.readCounter = 0;
            }

            log.info("ItemStream -> file opened. file={}", filePath);
        } catch (IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.", e);
            throw new ItemStreamException(e);
        }
    }

    private String[] readLine(String delimiter) throws IOException {
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                return null;
            }

            if (!line.isBlank()) {
                break;
            }
        }
        return line.split(delimiter, -1);
    }

    @Override
    public LandLotAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String[] line = readLine("\\|");
        readCounter++;

        if (line == null) return null;

        return LandLotAddressItem.builder()
                .legalDongCode(line[0])
                .sidoName(line[1])
                .sigunguName(line[2])
                .legalUmdName(line[3])
                .legalRiName(line[4])
                .isMountain(line[5])
                .landLotMainNo(line[6])
                .landLotSubNo(line[7])
                .roadNameCode(line[8])
                .isUnderground(line[9])
                .buildingMainNo(line[10])
                .buildingSubNo(line[11])
                .landLotSeq(line[12])
                .changeReasonCode(line[13])
                .build();
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(CURRENT_INDEX.name(), readCounter);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            br.close();
        } catch (IOException e) {
            log.error("대상 파일 close 중 에러가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }

}
