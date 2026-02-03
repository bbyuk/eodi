package com.bb.eodi.common.model;

import io.swagger.v3.oas.annotations.Parameter;

/**
 * 커서 요청 공통 파라미터
 */
public record CursorRequest(

        @Parameter(description = "다음 커서 ID", example = "21212")
        Long nextId,

        @Parameter(description = "커서 크기", example = "20")
        int size
) {
}
