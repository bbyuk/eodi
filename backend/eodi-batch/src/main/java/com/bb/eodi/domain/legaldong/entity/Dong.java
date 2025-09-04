package com.bb.eodi.domain.legaldong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "legal_dong_dong")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dong extends LegalDong {

    @Column(name = "code", length = 3, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_dong_sigungu_id")
    private Sigungu sigungu;
}
