package com.vedruna.simbad.persistence.repository;

import com.vedruna.simbad.persistence.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    //Buscar un animal por su id:
    Optional<Animal> findByAnimalId(Long animalId);
    List<Animal> findByRefugeRefugeId(Long refugeId);
    @Query("SELECT a FROM Animal a WHERE " +
            "(:species IS NULL OR a.species = :species) AND " +
            "(:size IS NULL OR a.size = :size) AND " +
            "(:energyLevel IS NULL OR a.energyLevel = :energyLevel) AND " +
            "(:province IS NULL OR a.refuge.province = :province) AND " +
            "a.statAdoption = 'Disponible para adoptar'")
    List<Animal> findAnimalsByCriteria(@Param("species") String species,
                                       @Param("size") String size,
                                       @Param("energyLevel") String energyLevel,
                                       @Param("province") String province);
}
