package com.bb.eodi.batch.domain.legaldong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 법정동코드 시도 데이터 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "legal_dong_sido")
public class LegalDongSido {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    // 코드
    @Column(name = "code", length = 2, unique = true)
    private String code;

    @Column(name = "name", length = 50)
    private String name;
}
