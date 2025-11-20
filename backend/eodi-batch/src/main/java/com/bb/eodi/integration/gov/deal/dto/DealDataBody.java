package com.bb.eodi.integration.gov.deal.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

/**
 * 부동산 실거래가 데이터 API 응답 바디
 */
@Data
public class DealDataBody <T> {
        @JacksonXmlElementWrapper(localName = "items")
        @JacksonXmlProperty(localName = "item")
        private List<T> items;

        // 전체 결과 수
        @JacksonXmlProperty(localName = "totalCount")
        private int totalCount;

        // 한 페이지 결과 수
        @JacksonXmlProperty(localName = "numberOfRows")
        private int numberOfRows;

        // 페이지 번호
        @JacksonXmlProperty(localName = "pageNo")
        private int pageNo;
}
