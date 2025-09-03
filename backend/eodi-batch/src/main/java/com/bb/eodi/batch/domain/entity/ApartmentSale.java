package com.bb.eodi.batch.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * 아파트 매매 데이터 엔티티
 */
@Entity
@Table(name = "apartment_sale")
public class ApartmentSale extends Sale {

    // 단지명
    @Column(name = "complex_name")
    private String complexName;

    // 아파트 동
    @Column(name = "apartment_building")
    private String apartmentBuilding;

    // 층
    @Column(name = "floor")
    private int floor;

    // 등기일자
    @Column(name = "registration_date")
    private LocalDate registrationDate;

}
