package com.vedruna.simbad.controllers;

import com.vedruna.simbad.dto.RefugeDTO;
import com.vedruna.simbad.jwt.JwtService;
import com.vedruna.simbad.persistence.model.Refuge;
import com.vedruna.simbad.services.RefugeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SimbadAPI/v1/Refuge")
public class RefugeController {

    @Autowired
    private RefugeServiceI refugeServiceI;

    @Autowired
    private JwtService jwtService;

    /** GET **/
    @GetMapping("/getRefuge/{refugeId}")
    private ResponseEntity<RefugeDTO> getRefuge(@PathVariable Long refugeId) {
        RefugeDTO refuge = refugeServiceI.getRefuge(refugeId);
        if (refuge != null) {
            return ResponseEntity.ok(refuge);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** PUT **/
    @PutMapping("/editRefuge")
    private ResponseEntity<RefugeDTO> editRefuge(
            @RequestParam(required = false) @Nullable String name,
            @RequestParam(required = false) @Nullable String typeRefuge,
            @RequestParam(required = false) @Nullable String cif,
            @RequestParam(required = false) @Nullable String province,
            @RequestParam(required = false) @Nullable String street,
            @RequestParam(required = false) @Nullable String codPost,
            @RequestParam(required = false) @Nullable String phone) {

        try {
            String emailToken = SecurityContextHolder.getContext().getAuthentication().getName();
            Long refugeId = jwtService.getRefugeIdFromToken(emailToken);

            Refuge refuge = new Refuge();
            refuge.setName(name != null ? name.replaceAll("\"", "") : null);
            refuge.setTypeRefuge(typeRefuge != null ? typeRefuge.replaceAll("\"", "") : null);
            refuge.setCif(cif != null ? cif.replaceAll("\"", "") : null);
            refuge.setProvince(province != null ? province.replaceAll("\"", "") : null);
            refuge.setStreet(street != null ? street.replaceAll("\"", "") : null);
            refuge.setPostCode(codPost != null ? codPost.replaceAll("\"", "") : null);
            refuge.setPhone(phone != null ? phone.replaceAll("\"", "") : null);

            RefugeDTO updatedRefuge = refugeServiceI.editRefuge(refugeId, refuge);

            if (updatedRefuge != null) {
                return ResponseEntity.ok(updatedRefuge);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /** DELETE **/
    @DeleteMapping("/delete/{refugeId}")
    private ResponseEntity<String> delete(@PathVariable Long refugeId) {
        refugeServiceI.deleteRefugio(refugeId);
        return ResponseEntity.ok("Refugio eliminado.");
    }
}
