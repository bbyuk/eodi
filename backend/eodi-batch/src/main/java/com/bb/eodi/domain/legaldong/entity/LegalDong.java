package com.bb.eodi.domain.legaldong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class LegalDong {
    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;

    @Column(name = "name", length = 50)
    protected String name;

    public LegalDong(String name) {
        this.name = name;
    }
}
