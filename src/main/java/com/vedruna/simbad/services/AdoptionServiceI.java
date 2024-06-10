package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.AdoptionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdoptionServiceI {

    // Crear adopción:
    boolean createAdoption(Long userId, Long animalId);

    // Buscar adopción por ID:
    AdoptionDTO getAdoptionById(Long adoptionId);

    // Conseguir lista de adopciones con filtros:
    List<AdoptionDTO> getAdoptionsByUserId(Long userId);
    List<AdoptionDTO> getAdoptionsByRefugeId(Long refugeId);

    // Editar estado de adopción:
    AdoptionDTO updateStatusAdoption(Long adoptionId);
    AdoptionDTO cancelStatusAdoption(Long adoptionId);
}
