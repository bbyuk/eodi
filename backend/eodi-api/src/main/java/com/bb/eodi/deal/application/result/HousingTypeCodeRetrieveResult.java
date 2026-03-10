package com.bb.eodi.deal.application.result;

/**
 * 주택유형코드 조회 결과
 */
public record HousingTypeCodeRetrieveResult(
        String code,
        String name
) { }
