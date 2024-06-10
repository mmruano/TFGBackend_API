package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.AnimalDTO;
import com.vedruna.simbad.persistence.model.Animal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AnimalServiceI {

    // Crear animal:
    void createAnimal(Long refugeId, Animal animal) throws IOException;

    // Buscar animal:
    AnimalDTO getAnimal(Long animalId);

    // Conseguir lista de animales:
    List<AnimalDTO> getAnimals(String species, String size, String energyLevel, String province);

    List<AnimalDTO> getAnimalsByRefugeId(Long refugeId);

    // Editar animal:
    AnimalDTO editAnimal(Long animalId, AnimalDTO animalDTO) throws IOException;

    // Borrar animal:
    void deleteAnimal(Long animalId);
}
