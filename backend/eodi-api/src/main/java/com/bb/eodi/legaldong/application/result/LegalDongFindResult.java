package com.bb.eodi.legaldong.application.result;

import lombok.Builder;
import lombok.Data;

/**
 * 법정동 조회 Application result
 */
@Data
@Builder
public class LegalDongFindResult {
    private Long id;
    private String code;
    private String name;
    private String displayName;
}
