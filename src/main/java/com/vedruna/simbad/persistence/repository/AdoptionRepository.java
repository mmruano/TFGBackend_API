package com.vedruna.simbad.persistence.repository;

import com.vedruna.simbad.persistence.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    // Buscar una adopción por su id:
    Optional<Adoption> findByAdoptionId(Long adoptionId);

    // Buscar adopciones por ID de usuario y estado de adopción:
    List<Adoption> findByUserUserId(Long userId);

    List<Adoption> findByRefugeRefugeId(Long refugeId);

    @Query("SELECT a FROM Adoption a WHERE a.user.id = :userId AND a.animal.id = :animalId")
    Optional<Adoption> findByUserIdAndAnimalId(@Param("userId") Long userId, @Param("animalId") Long animalId);
}
