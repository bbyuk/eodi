package com.bb.eodi.legaldong.domain.read;

import com.querydsl.core.annotations.QueryProjection;

/**
 * 법정동 read query model
 *      부모 법정동 명과 함께 조회
 * @param id 법정동ID
 * @param code 법정동코드
 * @param sidoCode 시도코드
 * @param sigunguCode 시군구코드
 * @param dongCode 동코드
 * @param name 법정동명
 * @param legalDongOrder 법정동순서
 * @param parentId 부모법정동ID
 * @param parentName 부모법정동명
 * @param active 활성여부
 */
public record LegalDongWithParentName(
        Long id,
        String code,
        String sidoCode,
        String sigunguCode,
        String dongCode,
        String name,
        int legalDongOrder,
        Long parentId,
        String parentName,
        boolean active
) {
    @QueryProjection
    public LegalDongWithParentName {}
}
