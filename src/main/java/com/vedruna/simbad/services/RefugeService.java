package com.vedruna.simbad.services;

import com.vedruna.simbad.dto.RefugeDTO;
import com.vedruna.simbad.persistence.model.Refuge;
import com.vedruna.simbad.persistence.repository.RefugeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefugeService implements RefugeServiceI {

    @Autowired
    private RefugeRepository refugeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RefugeDTO getRefuge(Long refugeId) {
        Optional<Refuge> refugeOp = refugeRepository.findByRefugeId(refugeId);
        return refugeOp.map(RefugeDTO::new).orElse(null);
    }

    @Override
    public RefugeDTO editRefuge(Long refugeId, Refuge refugeDTO) {
        Optional<Refuge> refugeOp = refugeRepository.findByRefugeId(refugeId);
        if (refugeOp.isPresent()) {
            Refuge refuge = refugeOp.get();

            if (refugeDTO.getName() != null) {
                refuge.setName(refugeDTO.getName());
            }
            if (refugeDTO.getTypeRefuge() != null) {
                refuge.setTypeRefuge(refugeDTO.getTypeRefuge());
            }
            if (refugeDTO.getCif() != null) {
                refuge.setCif(refugeDTO.getCif());
            }
            if (refugeDTO.getStreet() != null) {
                refuge.setStreet(refugeDTO.getStreet());
            }
            if (refugeDTO.getProvince() != null) {
                refuge.setProvince(refugeDTO.getProvince());
            }
            if (refugeDTO.getPostCode() != null) {
                refuge.setPostCode(refugeDTO.getPostCode());
            }
            if (refugeDTO.getPhone() != null) {
                refuge.setPhone(refugeDTO.getPhone());
            }

            refugeRepository.save(refuge);
            return new RefugeDTO(refuge);
        }
        return null;
    }

    @Override
    public void deleteRefugio(Long refugeId) {
        Optional<Refuge> refugeOp = refugeRepository.findByRefugeId(refugeId);
        refugeOp.ifPresent(refugeRepository::delete);
    }
}
