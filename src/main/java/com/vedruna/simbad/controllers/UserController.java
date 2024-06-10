package com.vedruna.simbad.controllers;

import com.vedruna.simbad.dto.UserDTO;
import com.vedruna.simbad.jwt.JwtService;
import com.vedruna.simbad.persistence.model.User;
import com.vedruna.simbad.services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/SimbadAPI/v1/User")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private JwtService jwtService;

    /** GET **/
    @GetMapping("/getUser/{userId}")
    private ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        UserDTO user = userServiceI.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserInfoByToken")
    private ResponseEntity<UserDTO> getUserInfoByToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = jwtService.getUserIdFromToken(email);
        UserDTO user = userServiceI.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** PUT **/
    @PutMapping("/editUser")
    private ResponseEntity<UserDTO> editUser(
            @RequestParam(required = false) @Nullable String name,
            @RequestParam(required = false) @Nullable String surname,
            @RequestParam(required = false) @Nullable String gender,
            @RequestParam(required = false) @Nullable String province,
            @RequestParam(required = false) @Nullable String codPost,
            @RequestParam(required = false) @Nullable String phone) {

        try {
            String emailToken = SecurityContextHolder.getContext().getAuthentication().getName();
            Long userId = jwtService.getUserIdFromToken(emailToken);

            User user = new User();
            user.setName(name != null ? name.replaceAll("\"", "") : null);
            user.setSurname(surname != null ? surname.replaceAll("\"", "") : null);
            user.setGender(gender != null ? gender.replaceAll("\"", "") : null);
            user.setProvince(province != null ? province.replaceAll("\"", "") : null);
            user.setPostCode(codPost != null ? codPost.replaceAll("\"", "") : null);
            user.setPhone(phone != null ? phone.replaceAll("\"", "") : null);

            UserDTO updatedUser = userServiceI.editUser(userId, user);

            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /** DELETE **/
    @DeleteMapping("/delete/{userId}")
    private ResponseEntity<String> delete(@PathVariable Long userId) {
        userServiceI.deleteUser(userId);
        return ResponseEntity.ok("Usuario eliminado.");
    }
}
