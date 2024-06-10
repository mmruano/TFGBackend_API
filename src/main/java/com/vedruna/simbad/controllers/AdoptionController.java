package com.vedruna.simbad.controllers;

import com.vedruna.simbad.dto.AdoptionDTO;
import com.vedruna.simbad.jwt.JwtService;
import com.vedruna.simbad.persistence.model.Adoption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.vedruna.simbad.services.AdoptionServiceI;

import java.util.List;

@RestController
@RequestMapping("/SimbadAPI/v1/Adoption")
public class AdoptionController {

    @Autowired
    private AdoptionServiceI adoptionServiceI;
    @Autowired
    private JwtService jwtService;

    /** GET **/
    // Buscar adopción por ID:
    @GetMapping("/getAdoption/{adoptionId}")
    public ResponseEntity<AdoptionDTO> getAdoption(@PathVariable Long adoptionId) {
        AdoptionDTO adoption = adoptionServiceI.getAdoptionById(adoptionId);
        if (adoption != null) {
            return ResponseEntity.ok(adoption);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Conseguir lista de adopciones por estado de adopción:
    @GetMapping("/getAdoptionsByUserId")
    public ResponseEntity<List<AdoptionDTO>> getAdoptionsByUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = jwtService.getUserIdFromToken(email);
        List<AdoptionDTO> adoptions = adoptionServiceI.getAdoptionsByUserId(userId);
        if (adoptions != null && !adoptions.isEmpty()) {
            return ResponseEntity.ok(adoptions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAdoptionsByRefugeId")
    public ResponseEntity<List<AdoptionDTO>> getAdoptionsByRefugeId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long refugeId = jwtService.getRefugeIdFromToken(email);
        List<AdoptionDTO> adoptions = adoptionServiceI.getAdoptionsByRefugeId(refugeId);
        if (adoptions != null && !adoptions.isEmpty()) {
            return ResponseEntity.ok(adoptions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** POST **/
    // Crear adopción:
    @PostMapping("/createAdoption/{animalId}")
    public ResponseEntity<String> createAdoption(@PathVariable Long animalId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = jwtService.getUserIdFromToken(email);
        boolean created = adoptionServiceI.createAdoption(userId, animalId);
        if (created) {
            return ResponseEntity.ok("Adopción creada.");
        } else {
            return ResponseEntity.status(409).body("Ya existe una adopción para este usuario y animal.");
        }
    }

    /** PUT **/
    @PutMapping("/updateStatusAdoption/{adoptionId}")
    public ResponseEntity<String> updateStatusAdoption(@PathVariable Long adoptionId) {
        AdoptionDTO updatedAdoption = adoptionServiceI.updateStatusAdoption(adoptionId);
        if (updatedAdoption != null) {
            return ResponseEntity.ok("Estado de la adopción editado.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cancelStatusAdoption/{adoptionId}")
    public ResponseEntity<String> cancelStatusAdoption(@PathVariable Long adoptionId) {
        AdoptionDTO updatedAdoption = adoptionServiceI.cancelStatusAdoption(adoptionId);
        if (updatedAdoption != null) {
            return ResponseEntity.ok("Estado de la adopción editado.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
