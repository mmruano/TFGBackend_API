package com.vedruna.simbad.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vedruna.simbad.persistence.model.Refuge;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefugeRepository extends JpaRepository<Refuge, Long> {

    //Buscar un refugio por su id:
    Optional<Refuge> findByRefugeId(Long refugeId);

    //Buscar un refugio por su nombre:
    Optional<Refuge> findByName(String name);

    //Buscar un refugio por su email:
    Optional<Refuge> findByEmail(String email) ;
}
