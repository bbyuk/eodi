package com.bb.eodi.batch.job.address.reader;

import com.bb.eodi.batch.job.address.dto.RoadNameAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 도로명주소 단일 ItemReader
 */
@Slf4j
@StepScope
@Component
public class RoadNameAddressItemReader implements ResourceAwareItemReaderItemStream<RoadNameAddressItem> {

    private Resource resource;
    private BufferedReader br;
    private int readCounter = 0;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = new BufferedReader(new InputStreamReader(resource.getInputStream(), Charset.forName("EUC-KR")));
            if (executionContext.containsKey("currentIndex")) {
                int lastIndex = executionContext.getInt("currentIndex");
                log.info("Read last index: {}", lastIndex);

                for (int i = 0; i < lastIndex; i++) {
                    br.readLine();
                }

                this.readCounter = lastIndex;;
            }
            else {
                this.readCounter = 0;
            }

            log.debug("ItemStream file open. file={}", resource.getFile().getAbsolutePath());
        }
        catch(IOException e) {
            log.error("대상 파일을 오픈하는 도중 문제가 발생했습니다.", e);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public RoadNameAddressItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line;

        while(true) {
            line = br.readLine();
            if (line == null) {
                return null;
            }

            if (!line.isBlank()) {
                break;
            }
        }

        readCounter++;

        String[] split = line.split("\\|", -1);

        try {
            return RoadNameAddressItem
                    .builder()
                    .sigunguCode(split[0])
                    .roadNameNo(split[1])
                    .roadName(split[2])
                    .engRoadName(split[3])
                    .umdSeq(split[4])
                    .sidoName(split[5])
                    .sigunguName(split[6])
                    .umdGb(split[7])
                    .umdCode(split[8])
                    .umdName(split[9])
                    .parentRoadNameNo(split[10])
                    .parentRoadName(split[11])
                    .useYn(split[12])
                    .changeHistoryReason(split[13])
                    .changeHistoryInfo(split[14])
                    .engSidoName(split[15])
                    .engSigunguName(split[16])
                    .engUmdName(split[17])
                    .announcementDate(split[18])
                    .expirationDate(split.length == 20 ? split[19] : null)
                    .build();
        }
        catch(ArrayIndexOutOfBoundsException e) {
            log.error("매핑 실패 : {}", e.getMessage(), e);
            log.error("line={}", line);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("currentIndex", readCounter);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (br != null) br.close();
        } catch (IOException e) {
            log.error("대상 파일을 닫는 중 문제가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }
}
