package com.vedruna.simbad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vedruna.simbad.persistence.model.Animal;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnimalDTO implements Serializable {

    private Long id;
    private String name;
    private String province;
    private String gender;
    private String age;
    private String birthDate;
    private String species;
    private String size;
    private String energyLevel;
    private String statAdoption;
    private String health;
    private String description;
    private String entryDate;
    private String departureDate;
    private String photo;
    private Long refugeId;
    private String refugeName;
    private String refugeStreet;
    private String refugeEmail;
    private String refugeProvince;
    private String refugePhone;
    private String refugeCodePost;

    public AnimalDTO() {
    }

    public AnimalDTO(Animal animal) {
        this.id = animal.getAnimalId();
        this.name = animal.getName();
        this.province = animal.getRefuge().getProvince();
        this.gender = animal.getGender();
        this.age = animal.getAge();
        this.birthDate = String.valueOf(animal.getBirthDate());
        this.species = animal.getSpecies();
        this.size = animal.getSize();
        this.energyLevel = animal.getEnergyLevel();
        this.statAdoption = animal.getStatAdoption();
        this.health = animal.getHealth();
        this.description = animal.getDescription();
        this.entryDate = String.valueOf(animal.getEntryDate());
        this.departureDate = String.valueOf(animal.getDepartureDate());
        this.photo = animal.getPhoto();
        this.refugeId = animal.getRefuge().getRefugeId();
        this.refugeName = animal.getRefuge().getName();
        this.refugeStreet = animal.getRefuge().getStreet();
        this.refugeEmail = animal.getRefuge().getEmail();
        this.refugeProvince = animal.getRefuge().getProvince();
        this.refugePhone = animal.getRefuge().getPhone();
        this.refugeCodePost = animal.getRefuge().getPostCode();
    }
}
