package com.vedruna.simbad.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vedruna.simbad.persistence.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Buscar un usuario por su id:
    Optional<User> findByUserId(Long userId);

    //Buscar un usuario por su nombre:
    Optional<User> findByName(String name);

    //Buscar un usuario por su email:
    Optional<User> findByEmail(String email);
}
