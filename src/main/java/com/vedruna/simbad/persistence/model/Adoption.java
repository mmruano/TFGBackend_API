package com.vedruna.simbad.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "adoption")
public class Adoption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_adoption_id")
    private Long adoptionId;

    // Clave foránea que hace referencia a la tabla usuario, a la columna u_usuario_id.
    @OneToOne
    @JoinColumn(name = "ad_user_id", referencedColumnName = "u_user_id")
    private User user;

    // Clave foránea que hace referencia a la tabla animal, a la columna a_animal_id.
    @OneToOne
    @JoinColumn(name = "ad_animal_id", referencedColumnName = "a_animal_id")
    private Animal animal;

    // Clave foránea que hace referencia a la tabla refugio, a la columna r_refugio_id.
    @ManyToOne
    @JoinColumn(name = "ad_refuge_id")
    private Refuge refuge;

    @Column(name = "ad_start_date")
    private LocalDate startDate;

    @Column(name = "ad_change_date")
    private LocalDate changeDate;

    @Column(name = "ad_status_adoption")
    private String statusAdoption;
}
