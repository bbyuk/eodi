package com.bb.eodi.deal.application.result.stream;

/**
 * 지역 추천 조회 스트리밍 count 증분 event
 * @param type event type
 * @param id 대상 법정동 ID
 * @param count 증분 count
 */
public record DeltaCountEvent(
        String type,
        long id,
        int count
) implements RegionStreamEvent {
    public DeltaCountEvent(long id, int count) {
        this("COUNT", id, count);
    }
}
