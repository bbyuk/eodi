package com.bb.eodi.address.job.reader;

import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LandLotAddressUpdateItemReader implements ResourceAwareItemReaderItemStream<LandLotAddressItem> {

    private static final String CONTEXT_LINE_NUMBER = "lineNumber";

    private Resource resource;
    private BufferedReader reader;
    private long lineNumber = 0;

    // MultiResourceItemReader가 파일 바뀔 때마다 호출
    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        this.reader = null;
        this.lineNumber = 0;
    }

    // Step 시작 / 재시작 시 호출
    @Override
    public void open(ExecutionContext context) throws ItemStreamException {
        try {
            this.lineNumber = context.getLong(CONTEXT_LINE_NUMBER, 0);

            this.reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
            );

            // 재시작 시 이미 처리한 라인 스킵
            for (long i = 0; i < lineNumber; i++) {
                reader.readLine();
            }

            log.info("📂 Open resource: {}, startLine={}",
                    resource.getFilename(), lineNumber);

        } catch (IOException e) {
            throw new ItemStreamException("Reader open failed", e);
        }
    }

    @Override
    public LandLotAddressItem read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        String line = reader.readLine();
        if (line == null) {
            return null; // 파일 끝 → MultiResourceItemReader가 다음 파일로 전환
        }

        lineNumber++;

        return parse(line);
    }

    @Override
    public void update(ExecutionContext context) throws ItemStreamException {
        context.putLong(CONTEXT_LINE_NUMBER, lineNumber);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            log.warn("Reader close failed", e);
            throw new RuntimeException(e);
        }
    }

    private LandLotAddressItem parse(String line) {
        String[] split = line.split("\\|", -1);

        if (split == null) return null;

        try {
            return LandLotAddressItem.builder()
                    .manageNo(split[0])              // 도로명주소관리번호
                    .legalDongCode(split[1])         // 법정동코드
                    .sidoName(split[2])              // 시도명
                    .sigunguName(split[3])           // 시군구명
                    .umdName(split[4])               // 법정읍면동명
                    .riName(split[5])                // 법정리명
                    .isMountain(split[6])            // 산여부
                    .landLotMainNo(split[7])         // 지번본번
                    .landLotSubNo(split[8])          // 지번부번
                    .roadNameCode(split[9])          // 도로명코드
                    .isUnderground(split[10])        // 지하여부
                    .buildingMainNo(split[11])       // 건물본번
                    .buildingSubNo(split[12])        // 건물부번
                    .updateReasonCode(split[13])     // 이동사유코드
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("매핑 실패 : {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
