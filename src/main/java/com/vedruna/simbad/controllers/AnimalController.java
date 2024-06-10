package com.vedruna.simbad.controllers;

import com.vedruna.simbad.dto.AnimalDTO;
import com.vedruna.simbad.jwt.JwtService;
import com.vedruna.simbad.persistence.model.Animal;
import com.vedruna.simbad.services.AnimalServiceI;
import com.vedruna.simbad.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/SimbadAPI/v1/Animal")
public class AnimalController {

    @Autowired
    private AnimalServiceI animalServiceI;
    @Autowired
    private ImageService imageService;
    @Autowired
    private JwtService jwtService;

    /** GET **/
    @GetMapping("/getAnimal/{animalId}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable Long animalId) {
        AnimalDTO animal = animalServiceI.getAnimal(animalId);
        if (animal != null) {
            return ResponseEntity.ok(animal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAnimals")
    public ResponseEntity<List<AnimalDTO>> getAnimals(@RequestParam(required = false) String species,
                                                      @RequestParam(required = false) String size,
                                                      @RequestParam(required = false) String energyLevel,
                                                      @RequestParam(required = false) String province) {
        List<AnimalDTO> animals = animalServiceI.getAnimals(species, size, energyLevel, province);
        if (animals != null && !animals.isEmpty()) {
            return ResponseEntity.ok(animals);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAnimalsByRefugeId")
    public ResponseEntity<List<AnimalDTO>> getAnimalsByRefugeId() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Long refugeId = jwtService.getRefugeIdFromToken(email);
            List<AnimalDTO> animals = animalServiceI.getAnimalsByRefugeId(refugeId);
            if (animals != null && !animals.isEmpty()) {
                return ResponseEntity.ok(animals);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** POST **/
    @PostMapping("/createAnimal")
    public ResponseEntity<String> createAnimal(
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam String age,
            @RequestParam String birthDate,
            @RequestParam String species,
            @RequestParam String size,
            @RequestParam String energyLevel,
            @RequestParam String health,
            @RequestParam String description,
            @RequestParam MultipartFile photo) {
        try {
            birthDate = birthDate.replaceAll("\"", "");
            name = name.replaceAll("\"", "");
            gender = gender.replaceAll("\"", "");
            age = age.replaceAll("\"", "");
            species = species.replaceAll("\"", "");
            size = size.replaceAll("\"", "");
            energyLevel = energyLevel.replaceAll("\"", "");
            health = health.replaceAll("\"", "");
            description = description.replaceAll("\"", "");

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Long refugeId = jwtService.getRefugeIdFromToken(email);
            String photoUrl = imageService.saveImage(photo);

            Animal animal = new Animal();
            animal.setName(name);
            animal.setGender(gender);
            animal.setAge(age);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                animal.setBirthDate(LocalDate.parse(birthDate, formatter));
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Invalid date format. Please use yyyy-MM-dd.");
            }

            animal.setSpecies(species);
            animal.setSize(size);
            animal.setEnergyLevel(energyLevel);
            animal.setHealth(health);
            animal.setDescription(description);

            LocalDate entryDate = LocalDate.now();
            animal.setEntryDate(entryDate);

            animal.setPhoto(photoUrl);

            animalServiceI.createAnimal(refugeId, animal);

            return ResponseEntity.ok("Animal created.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating animal.");
        }
    }

    /** PUT **/
    @PutMapping("/editAnimal/{animalId}")
    public ResponseEntity<String> editAnimal(
            @PathVariable Long animalId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String birthDate,
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String energyLevel,
            @RequestParam(required = false) String health,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) MultipartFile photo) {
        try {
            AnimalDTO existingAnimal = animalServiceI.getAnimal(animalId);
            if (existingAnimal == null) {
                return ResponseEntity.notFound().build();
            }

            if (name != null) {
                existingAnimal.setName(name.replaceAll("\"", ""));
            }
            if (gender != null) {
                existingAnimal.setGender(gender.replaceAll("\"", ""));
            }
            if (age != null) {
                existingAnimal.setAge(age.replaceAll("\"", ""));
            }
            if (birthDate != null) {
                existingAnimal.setBirthDate(birthDate.replaceAll("\"", ""));
            }
            if (species != null) {
                existingAnimal.setSpecies(species.replaceAll("\"", ""));
            }
            if (size != null) {
                existingAnimal.setSize(size.replaceAll("\"", ""));
            }
            if (energyLevel != null) {
                existingAnimal.setEnergyLevel(energyLevel.replaceAll("\"", ""));
            }
            if (health != null) {
                existingAnimal.setHealth(health.replaceAll("\"", ""));
            }
            if (description != null) {
                existingAnimal.setDescription(description.replaceAll("\"", ""));
            }

            if (photo != null && !photo.isEmpty()) {
                String photoUrl = imageService.saveImage(photo);
                existingAnimal.setPhoto(photoUrl);
            }

            animalServiceI.editAnimal(animalId, existingAnimal);

            return ResponseEntity.ok("Animal edited successfully.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error.");
        }
    }

    /** DELETE **/
    @DeleteMapping("/delete/{animalId}")
    public ResponseEntity<String> delete(@PathVariable Long animalId) {
        animalServiceI.deleteAnimal(animalId);
        return ResponseEntity.ok("Animal deleted.");
    }
}
