package com.bb.eodi.deal.application.model;

import java.util.Map;

/**
 * 크로스도메인 법정동 정보
 */
public record LegalDongInfo(
    Long id,
    String code,
    String name,
    int order,
    Long parentId,
    Map<Long, LegalDongInfo> children
) {

}
