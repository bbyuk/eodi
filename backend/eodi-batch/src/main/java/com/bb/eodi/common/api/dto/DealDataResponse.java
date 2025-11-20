package com.bb.eodi.common.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 부동산 실거래가 데이터 API 응답 DTO
 */
@JacksonXmlRootElement(localName = "response")
public record DealDataResponse<T> (

        @JacksonXmlProperty(localName = "header")
        DealDataHeader header,
        @JacksonXmlProperty(localName = "body")
        DealDataBody<T> body
) {
}
