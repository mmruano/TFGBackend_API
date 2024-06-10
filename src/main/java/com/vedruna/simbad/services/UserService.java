package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.UserDTO;
import com.vedruna.simbad.persistence.model.User;
import com.vedruna.simbad.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceI {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUser(Long userId) {
        Optional<User> userOp = userRepository.findByUserId(userId);
        return userOp.map(UserDTO::new).orElse(null);
    }

    @Override
    public UserDTO editUser(Long userId, User userDTO) {
        Optional<User> userOp = userRepository.findByUserId(userId);
        if (userOp.isPresent()) {
            User user = userOp.get();

            if (userDTO.getName() != null) {
                user.setName(userDTO.getName());
            }
            if (userDTO.getSurname() != null) {
                user.setSurname(userDTO.getSurname());
            }
            if (userDTO.getProvince() != null) {
                user.setProvince(userDTO.getProvince());
            }
            if (userDTO.getPostCode() != null) {
                user.setPostCode(userDTO.getPostCode());
            }
            if (userDTO.getPhone() != null) {
                user.setPhone(userDTO.getPhone());
            }

            userRepository.save(user);
            return new UserDTO(user);
        }
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> userOp = userRepository.findByUserId(userId);
        userOp.ifPresent(userRepository::delete);
    }
}
