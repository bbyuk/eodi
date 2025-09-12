package com.bb.eodi.domain.legaldong.dto;

/**
 * 법정동 요약 View Dto
 * Cross domain context 제공용
 */
public interface LegalDongSummaryView {
    Long getId();
    Long getCode();
    String getSidoCode();
    String getSigunguCode();
    String getName();
}
