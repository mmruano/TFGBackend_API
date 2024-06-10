package com.vedruna.simbad.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "animal")
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_animal_id")
    private Long animalId;

    @ManyToOne
    @JoinColumn(name = "a_refuge_id")
    private Refuge refuge;

    @Column(name = "a_name")
    private String name;

    @Column(name = "a_gender")
    private String gender;

    @Column(name = "a_age")
    private String age;

    @Column(name = "a_birth_date")
    private LocalDate birthDate;

    @Column(name = "a_species")
    private String species;

    @Column(name = "a_size")
    private String size;

    @Column(name = "a_energy_level")
    private String energyLevel;

    @Column(name = "a_status_adoption")
    private String statAdoption;

    @Column(name = "a_health")
    private String health;

    @Column(name = "a_description")
    private String description;

    @Column(name = "a_entry_date")
    private LocalDate entryDate;

    @Column(name = "a_departure_date")
    private LocalDate departureDate;

    @Column(name = "a_photo")
    private String photo;
}
