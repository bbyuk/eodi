package com.bb.eodi.deal.application.result.stream;

/**
 * 지역 그룹 (시도) 스트리밍 event
 * @param type
 * @param code
 * @param name
 * @param displayName
 */
public record RegionGroupEvent(
        String type,
        String code,
        String name,
        String displayName
) implements RegionStreamEvent {
    public RegionGroupEvent(String code, String name, String displayName) {
        this("GROUP", code, name, displayName);
    }
}
