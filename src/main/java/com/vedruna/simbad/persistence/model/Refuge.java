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
@Table(name = "refuge", uniqueConstraints = {@UniqueConstraint(columnNames = "r_email")})
public class Refuge implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_refuge_id")
    private Long refugeId;

    @Column(name = "r_name")
    private String name;

    @Column(name = "r_type_refuge")
    private String typeRefuge;

    @Column(name = "r_cif")
    private String cif;

    @Column(name = "r_street")
    private String street;

    @Column(name = "r_province")
    private String province;

    @Column(name = "r_post_Code")
    private String postCode;

    @Column(name = "r_phone")
    private String phone;

    @Column(name = "r_email", unique = true)
    private String email;

    @Column(name = "r_password")
    private String password;

    @Column(name = "r_creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "refuge", cascade = CascadeType.ALL)
    private List<Animal> animals;

    @OneToMany(mappedBy = "refuge", cascade = CascadeType.ALL)
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
