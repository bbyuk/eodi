package com.bb.eodi.batch.domain.legaldong.entity;

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
public class LegalDongSigungu {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "name", length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_dong_sido_id")
    private LegalDongSido legalDongSido;
}
