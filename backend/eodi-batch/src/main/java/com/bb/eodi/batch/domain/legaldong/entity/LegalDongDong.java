package com.bb.eodi.batch.domain.legaldong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "legal_dong_dong")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegalDongDong {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 3, unique = true)
    private String code;

    @Column(name = "name", length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_dong_sigungu_id")
    private LegalDongSigungu legalDongSigungu;
}
