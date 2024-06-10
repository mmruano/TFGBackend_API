package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.RefugeDTO;
import com.vedruna.simbad.persistence.model.Refuge;

public interface RefugeServiceI {

    /** Buscar **/
    //Buscar refugio:
    RefugeDTO getRefuge(Long refugeId);

    /** Editar **/
    // Editar cualquier atributo del refugio:
    RefugeDTO editRefuge(Long refugeId, Refuge refugeDTO);

    /** Borrar **/
    // Borrar refugio:
    void deleteRefugio(Long refugeId);
}
