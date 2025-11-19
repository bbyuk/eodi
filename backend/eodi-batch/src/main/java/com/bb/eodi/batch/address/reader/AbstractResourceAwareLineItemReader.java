package com.bb.eodi.batch.address.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * ResourceAwareLineItemReader 추상클래스
 * @param <T>
 */
@Slf4j
public abstract class AbstractResourceAwareLineItemReader<T> implements ResourceAwareItemReaderItemStream<T> {

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

   public String[] readLine(String delimiter) throws Exception {
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

       return line.split(delimiter, -1);
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
