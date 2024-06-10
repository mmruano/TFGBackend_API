package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.AdoptionDTO;
import com.vedruna.simbad.persistence.model.Adoption;
import com.vedruna.simbad.persistence.model.Animal;
import com.vedruna.simbad.persistence.model.User;
import com.vedruna.simbad.persistence.repository.RefugeRepository;
import com.vedruna.simbad.persistence.repository.UserRepository;
import com.vedruna.simbad.persistence.repository.AdoptionRepository;
import com.vedruna.simbad.persistence.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdoptionService implements AdoptionServiceI {

    @Autowired
    private AdoptionRepository adoptionRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean createAdoption(Long userId, Long animalId) {
        User userRequest = userRepository.findById(userId).orElse(null);
        Animal animalRequest = animalRepository.findById(animalId).orElse(null);

        if (userRequest != null && animalRequest != null) {
            Optional<Adoption> existingAdoption = adoptionRepository.findByUserIdAndAnimalId(userId, animalId);

            if (existingAdoption.isPresent()) {
                return false;
            } else {
                Adoption newAdoption = new Adoption();
                newAdoption.setUser(userRequest);
                newAdoption.setAnimal(animalRequest);
                newAdoption.setRefuge(animalRequest.getRefuge());
                newAdoption.setStartDate(LocalDate.now());
                newAdoption.setStatusAdoption("En progreso");

                adoptionRepository.save(newAdoption);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public AdoptionDTO getAdoptionById(Long adoptionId) {
        Adoption adoptionRequest = adoptionRepository.findByAdoptionId(adoptionId).orElse(null);
        if (adoptionRequest != null) {
            return new AdoptionDTO(adoptionRequest);
        }
        return null;
    }

    @Override
    public List<AdoptionDTO> getAdoptionsByUserId(Long userId) {
        List<Adoption> listAdoptions = adoptionRepository.findByUserUserId(userId);
        return listAdoptions.stream()
                .map(AdoptionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdoptionDTO> getAdoptionsByRefugeId(Long refugeId) {
        List<Adoption> listAdoptions = adoptionRepository.findByRefugeRefugeId(refugeId);
        return listAdoptions.stream()
                .map(AdoptionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AdoptionDTO updateStatusAdoption(Long adoptionId) {
        Optional<Adoption> adoptionOp = adoptionRepository.findByAdoptionId(adoptionId);
        if (adoptionOp.isPresent()) {
            Adoption adoption = adoptionOp.get();
            adoption.getAnimal().setStatAdoption("Adoptado");
            adoption.setStatusAdoption("Adoptado");
            adoption.setChangeDate(LocalDate.now());
            adoptionRepository.save(adoption);
            return new AdoptionDTO(adoption);
        }
        return null;
    }

    @Override
    public AdoptionDTO cancelStatusAdoption(Long adoptionId) {
        Optional<Adoption> adoptionOp = adoptionRepository.findByAdoptionId(adoptionId);
        if (adoptionOp.isPresent()) {
            Adoption adoption = adoptionOp.get();
            adoption.setStatusAdoption("Cancelado");
            adoption.setChangeDate(LocalDate.now());
            adoptionRepository.save(adoption);
            return new AdoptionDTO(adoption);
        }
        return null;
    }
}
