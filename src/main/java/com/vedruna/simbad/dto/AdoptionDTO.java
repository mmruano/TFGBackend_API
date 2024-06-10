package com.vedruna.simbad.dto;

import com.vedruna.simbad.persistence.model.Adoption;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AdoptionDTO implements Serializable {

    private Long id;
    private String userName;
    private String surname;
    private String province;
    private String email;
    private String phone;
    private String postCode;
    private String startDate;
    private String changeDate;
    private String statusAdoption;
    private String animalName;
    private String animalProvince;
    private String animalGender;
    private String animalAge;
    private String animalBirthDate;
    private String animalSpecies;
    private String animalSize;
    private String animalEnergyLevel;
    private String animalStatAdoption;
    private String animalHealth;
    private String animalDescription;
    private String animalEntryDate;
    private String animalDepartureDate;
    private String animalPhoto;
    private Long animalRefugeId;
    private String animalRefugeName;
    private String animalRefugeStreet;
    private String animalRefugeEmail;
    private String animalRefugeProvince;
    private String animalRefugePhone;
    private String animalRefugeCodePost;

    public AdoptionDTO() {
    }

    public AdoptionDTO(Adoption adoption) {
        this.id = adoption.getAdoptionId();
        this.userName = adoption.getUser().getName();
        this.surname = adoption.getUser().getSurname();
        this.province = adoption.getUser().getProvince();
        this.email = adoption.getUser().getEmail();
        this.phone = adoption.getUser().getPhone();
        this.postCode = adoption.getUser().getPostCode();
        this.startDate = String.valueOf(adoption.getStartDate());
        this.changeDate = String.valueOf(adoption.getChangeDate());
        this.statusAdoption = adoption.getStatusAdoption();
        this.animalPhoto = adoption.getAnimal().getPhoto();
        this.animalName = adoption.getAnimal().getName();
        this.animalProvince = adoption.getRefuge().getProvince();
        this.animalGender = adoption.getAnimal().getGender();
        this.animalAge = adoption.getAnimal().getAge();
        this.animalBirthDate = String.valueOf(adoption.getAnimal().getBirthDate());
        this.animalSpecies = adoption.getAnimal().getSpecies();
        this.animalSize = adoption.getAnimal().getSize();
        this.animalEnergyLevel = adoption.getAnimal().getEnergyLevel();
        this.animalStatAdoption = adoption.getAnimal().getStatAdoption();
        this.animalHealth = adoption.getAnimal().getHealth();
        this.animalDescription = adoption.getAnimal().getDescription();
        this.animalEntryDate = String.valueOf(adoption.getAnimal().getEntryDate());
        this.animalDepartureDate = String.valueOf(adoption.getAnimal().getDepartureDate());
        this.animalRefugeId = adoption.getRefuge().getRefugeId();
        this.animalRefugeName = adoption.getRefuge().getName();
        this.animalRefugeStreet = adoption.getRefuge().getStreet();
        this.animalRefugeEmail = adoption.getRefuge().getEmail();
        this.animalRefugeProvince = adoption.getRefuge().getProvince();
        this.animalRefugePhone = adoption.getRefuge().getPhone();
        this.animalRefugeCodePost = adoption.getRefuge().getPostCode();
    }
}
