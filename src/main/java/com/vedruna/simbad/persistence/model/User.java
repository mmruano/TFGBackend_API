package com.vedruna.simbad.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "u_email")})
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_user_id")
    private Long userId;

    @Column(name = "u_name")
    private String name;

    @Column(name = "u_surname")
    private String surname;

    @Column(name = "u_gender")
    private String gender;

    @Column(name = "u_province")
    private String province;

    @Column(name = "u_post_Code")
    private String postCode;

    @Column(name = "u_email", unique = true)
    private String email;

    @Column(name = "u_phone")
    private String phone;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Adoption> adoptions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
