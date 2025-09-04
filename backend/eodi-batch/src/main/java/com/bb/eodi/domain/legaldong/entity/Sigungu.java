package com.bb.eodi.domain.legaldong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 법정동코드 시군구 데이터 엔티티
 */
@Entity
@Getter
@Table(name = "legal_dong_sigungu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sigungu extends LegalDong {

    @Column(name = "code", length = 3, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_dong_sido_id")
    private Sido sido;
}
