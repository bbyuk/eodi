package com.bb.eodi.deal.application.result.stream;

/**
 * 지역 스트리밍 event (시군구)
 * @param type event type
 * @param id 지역 법정동 ID
 * @param groupCode 소속 지역 그룹 (시도) 법정동코드
 * @param code 법정동코드
 * @param name 지역명
 * @param displayName 노출지역명
 */
public record RegionEvent(
        String type,
        long id,
        String groupCode,
        String code,
        String name,
        String displayName
) implements RegionStreamEvent {

    public RegionEvent(long id, String groupCode, String code, String name, String displayName) {
        this("REGION", id, groupCode, code, name, displayName);
    }
}
