package com.bb.eodi.deal.application.result.stream;

/**
 * 지역 추천 스트리밍 조회 리턴 Event interface
 */
public sealed interface RegionStreamEvent permits RegionGroupEvent, RegionEvent, DeltaCountEvent {
    String type();
}
