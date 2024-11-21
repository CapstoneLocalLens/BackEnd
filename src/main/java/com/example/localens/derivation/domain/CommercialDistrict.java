package com.example.localens.derivation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "commercial_districts")
@NoArgsConstructor
@Getter
public class CommercialDistrict {

    @Id
    @Column(name = "district_uuid")
    private UUID districtUuid;

    @Column(name = "district_name")
    private String districtName;

}

