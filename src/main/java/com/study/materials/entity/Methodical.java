package com.study.materials.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "methodicals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Methodical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 256)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, length = 128)
    private String subject;

    @Column(name = "edition_year")
    private Integer editionYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private EducationalMaterial linkedMaterial;
}
