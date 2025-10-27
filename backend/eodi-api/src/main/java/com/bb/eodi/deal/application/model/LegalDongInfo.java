package com.bb.eodi.deal.application.model;

import java.util.Map;
import java.util.Set;

/**
 * 크로스도메인 법정동 정보
 */
public record LegalDongInfo(
    Long id,
    String code,
    String name,
    int order,
    Long parentId,
    Set<LegalDongInfo> children
) {

}
