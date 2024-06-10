package com.vedruna.simbad.dto;

import com.vedruna.simbad.persistence.model.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDTO implements Serializable {

    private String name;
    private String surname;
    private String gender;
    private String province;
    private String postCode;
    private String email;
    private String phone;
    private String creationDate;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.gender = user.getGender();
        this.province = user.getProvince();
        this.postCode = user.getPostCode();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.creationDate = String.valueOf(user.getCreationDate());
    }
}
