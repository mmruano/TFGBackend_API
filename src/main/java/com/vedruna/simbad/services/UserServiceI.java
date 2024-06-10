package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.UserDTO;
import com.vedruna.simbad.persistence.model.User;

public interface UserServiceI {

    /** Buscar **/
    //Buscar usuario:
    UserDTO getUser(Long userId);

    /** Editar **/
    // Editar cualquier atributo del usuario:
    UserDTO editUser(Long userId, User userDTO);

    /** Borrar **/
    // Borrar usuario:
    void deleteUser(Long userId);
}
