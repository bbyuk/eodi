package com.bb.eodi.batch.legaldong.load.model;

import java.util.List;
import java.util.Map;

/**
 * 법정동코드 API응답 성공 데이터
 */
public record StanReginCd(
    List<Map<String, Object>> head
){}
