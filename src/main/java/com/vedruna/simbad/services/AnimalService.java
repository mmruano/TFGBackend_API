package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.AnimalDTO;
import com.vedruna.simbad.persistence.model.Animal;
import com.vedruna.simbad.persistence.model.Refuge;
import com.vedruna.simbad.persistence.repository.AnimalRepository;
import com.vedruna.simbad.persistence.repository.RefugeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalService implements AnimalServiceI {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private RefugeRepository refugeRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public void createAnimal(Long refugeId, Animal animalRequest) throws IOException {
        Animal animal = new Animal();
        animal.setName(animalRequest.getName());
        Refuge refuge = refugeRepository.findByRefugeId(refugeId).orElse(null);
        animal.setRefuge(refuge);
        animal.setGender(animalRequest.getGender());
        animal.setAge(animalRequest.getAge());
        animal.setBirthDate(animalRequest.getBirthDate());
        animal.setSpecies(animalRequest.getSpecies());
        animal.setSize(animalRequest.getSize());
        animal.setEnergyLevel(animalRequest.getEnergyLevel());
        animal.setStatAdoption("Disponible para adoptar");
        animal.setHealth(animalRequest.getHealth());
        animal.setDescription(animalRequest.getDescription());
        animal.setEntryDate(LocalDate.now());
        animal.setPhoto(animalRequest.getPhoto());

        animalRepository.save(animal);
    }

    @Override
    public AnimalDTO getAnimal(Long animalId) {
        Optional<Animal> animal = animalRepository.findById(animalId);
        return animal.map(AnimalDTO::new).orElse(null);
    }

    @Override
    public List<AnimalDTO> getAnimals(String species, String size, String energyLevel, String province) {
        List<Animal> animals = animalRepository.findAnimalsByCriteria(species, size, energyLevel, province);
        return mapAnimalsToDTOs(animals);
    }

    @Override
    public List<AnimalDTO> getAnimalsByRefugeId(Long refugeId) {
        List<Animal> listAnimals = animalRepository.findByRefugeRefugeId(refugeId);
        return listAnimals.stream()
                .map(AnimalDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDTO editAnimal(Long animalId, AnimalDTO animalDTO) throws IOException {
        Optional<Animal> animalOp = animalRepository.findByAnimalId(animalId);
        if (animalOp.isPresent()) {
            Animal animal = animalOp.get();
            if (animalDTO.getName() != null) {
                animal.setName(animalDTO.getName());
            }
            if (animalDTO.getGender() != null) {
                animal.setGender(animalDTO.getGender());
            }
            if (animalDTO.getAge() != null) {
                animal.setAge(animalDTO.getAge());
            }
            if (animalDTO.getBirthDate() != null) {
                animal.setBirthDate(LocalDate.parse(animalDTO.getBirthDate()));
            }
            if (animalDTO.getSpecies() != null) {
                animal.setSpecies(animalDTO.getSpecies());
            }
            if (animalDTO.getSize() != null) {
                animal.setSize(animalDTO.getSize());
            }
            if (animalDTO.getEnergyLevel() != null) {
                animal.setEnergyLevel(animalDTO.getEnergyLevel());
            }
            if (animalDTO.getHealth() != null) {
                animal.setHealth(animalDTO.getHealth());
            }
            if (animalDTO.getDescription() != null) {
                animal.setDescription(animalDTO.getDescription());
            }
            if (animalDTO.getPhoto() != null) {
                animal.setPhoto(animalDTO.getPhoto());
            }

            animalRepository.save(animal);
            return new AnimalDTO(animal);
        }
        return null;
    }

    @Override
    public void deleteAnimal(Long animalId) {
        animalRepository.deleteById(animalId);
    }

    private List<AnimalDTO> mapAnimalsToDTOs(List<Animal> animals) {
        return animals.stream()
                .map(AnimalDTO::new)
                .collect(Collectors.toList());
    }
}
