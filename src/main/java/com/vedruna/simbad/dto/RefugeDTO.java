package com.vedruna.simbad.dto;

import com.vedruna.simbad.persistence.model.Refuge;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class RefugeDTO implements Serializable {

    private String name;
    private String typeRefuge;
    private String cif;
    private String street;
    private String province;
    private String postCode;
    private String phone;
    private String email;
    private String createDate;

    public RefugeDTO() {

    }

    public RefugeDTO(Refuge refuge) {
        this.name = refuge.getName();
        this.typeRefuge = refuge.getTypeRefuge();
        this.cif = refuge.getCif();
        this.street = refuge.getStreet();
        this.province = refuge.getProvince();
        this.postCode = refuge.getPostCode();
        this.phone = refuge.getPhone();
        this.email = refuge.getEmail();
        this.createDate = String.valueOf(refuge.getCreationDate());
    }
}
